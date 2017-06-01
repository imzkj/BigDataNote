package org.robby.mr.shortestpath2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import redis.clients.jedis.Jedis;

public class Map  extends Mapper<Text, Text, Text, Text>{

	Jedis jedis;
	List<String> allNodes;
	
	protected void setup(Context context){
		jedis = new Jedis("192.168.1.121");
		allNodes = jedis.lrange("all_nodes", 0, -1);
	}
	

	public static Node getNodeByName(String n, List<Node> L){
		for(Node node:L){
			if(n.equals(node.name)){
				return node;
			}
		}
		return null;
	}
	
	public static Node getShortestNode(List<Node> L){
		Node n = null;
		int d = Integer.MAX_VALUE;
		for(int i=0; i<L.size(); i++)
		{
			Node node = L.get(i);
			if(node.getDistance() <= d){
				n = node;
				d = n.getDistance();
			}
		}
		
		
		for(int i=0; i<L.size(); i++)
		{
			Node node = L.get(i);
			if(node.name.equals(n.name)){
				L.remove(node);
			}
		}
		return n;
	}
	
	public static boolean exist(String name, List<Node> L){
		for(Node node:L){
			if(name.equals(node.name)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected void map(Text key, Text value, Context context) throws IOException, InterruptedException{
		List<Node> T = new ArrayList<Node>();
		List<Node> S = new ArrayList<Node>();

		for(String n: allNodes){
			T.add(new Node(n, Integer.MAX_VALUE));
		}
		
		getNodeByName(key.toString(), T).setDistance(0);
		
		while(T.size() > 0){
			Node n = getShortestNode(T);
			System.out.println("1:" + n.name);
			List<String> adjNodes = jedis.lrange("node_" + n.name, 0, -1);
			int distance = n.getDistance() + 1;
			String bp;
			if(n.getBackpointer() == null)
				bp = n.name;
			else
				bp = n.getBackpointer() + ":" + n.name;
			for(String adjName:adjNodes){
				if(!exist(adjName, S)){
					Node adjNode = getNodeByName(adjName, T);
					if(adjNode.getDistance() > distance){
						adjNode.setDistance(distance);
						adjNode.setBackpointer(bp);
					}
				}
			}
			S.add(n);
		}
		
		for(Node n:S){
			Text t = new Text(key.toString() + "-" + n.name + " (" + n.getBackpointer() + ")");
			context.write(t, new Text(""));
		}
	}
}

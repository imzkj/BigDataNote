package org.robby.mr.shortestpath;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class Reduce extends Reducer<Text, Text, Text, Text>{
	private Text outValue = new Text();
	private String targetNode;
	
	
	public static enum PathCounter{
		TARGET_NODE_FOUND
	}
	
	protected void setup(Context context){
		System.out.println("Reduce setup targeNode=" + targetNode);
		targetNode = context.getConfiguration().get(Main.TARGET_NODE);
	}
	
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
		int minDistance = Integer.MAX_VALUE;
		System.out.println("Reduce.targeNode=" + targetNode);
		Node shortestAdjacentNode = null;
		Node originalNode = null;
		
		for(Text textValue : values){
			Node node = Node.fromMR(textValue.toString());
			if(node.containsAjacentNodes()){
				originalNode = node;
			}
			
			if(node.getDistance() < minDistance){
				minDistance = node.getDistance();
				shortestAdjacentNode = node;
			}
		}
		
		if(shortestAdjacentNode != null){
			originalNode.setDistance(minDistance);
			originalNode.setBackpointer(shortestAdjacentNode.getBackpointer());
		}
		
		outValue.set(originalNode.toString());
		context.write(key, outValue);
		
		if(targetNode.equals(key.toString()) && minDistance != Integer.MAX_VALUE){
			System.out.println("target node found" + targetNode);
			Counter counter = context.getCounter(PathCounter.TARGET_NODE_FOUND);
			counter.increment(minDistance);
		}
	}
}

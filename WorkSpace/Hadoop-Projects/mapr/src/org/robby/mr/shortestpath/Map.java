package org.robby.mr.shortestpath;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class Map  extends Mapper<Text, Text, Text, Text>{
	private Text outKey = new Text();
	private Text outValue = new Text();
	
	@Override
	protected void map(Text key, Text value, Context context) throws IOException, InterruptedException{
		Node node = Node.fromMR(value.toString());
		context.write(key, value);
		
		if(node.isDistanceSet()){
			int neighborDistance = node.getDistance() + 1;
			String backpointer = node.constructBackpointer(key.toString());
			
			for(int i=0; i<node.getAdjacentNodeNames().length; i++){
				String neighbor = node.getAdjacentNodeNames()[i];
				
				outKey.set(neighbor);
				
				Node adjacentNode = new Node()
					.setDistance(neighborDistance)
					.setBackpointer(backpointer);
				
				outValue.set(adjacentNode.toString());
				context.write(outKey, outValue);
			}
		}
	}
}

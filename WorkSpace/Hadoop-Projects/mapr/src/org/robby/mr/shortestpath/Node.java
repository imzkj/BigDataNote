package org.robby.mr.shortestpath;


import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

public class Node {
	private String backpointer;
	private String[] adjacentNodeNames;
	
	public static final char fieldSeparator = '\t';
	
	private int distance = Integer.MAX_VALUE;
	
	public int getDistance(){
		return distance;
	}
	
	public Node setDistance(int distance){
		this.distance = distance;
		return this;
	}
	
	public String getBackpointer(){
		return backpointer;
	}
	
	public Node setBackpointer(String backpointer){
		this.backpointer = backpointer;
		return this;
	}
	
	public String constructBackpointer(String name){
		StringBuilder backpointers = new StringBuilder();
		if(StringUtils.trimToNull(getBackpointer()) != null){
			backpointers.append(getBackpointer()).append(":");
		}
		backpointers.append(name);
		return backpointers.toString();
	}
	
	public String[] getAdjacentNodeNames(){
		return adjacentNodeNames;
	}
	
	public Node setAdjacentNodeNames(String[] adjacentNodeNames){
		this.adjacentNodeNames = adjacentNodeNames;
		return this;
	}
	
	public boolean containsAjacentNodes(){
		return adjacentNodeNames != null;
	}
	
	public boolean isDistanceSet(){
		return distance != Integer.MAX_VALUE;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(distance)
			.append(fieldSeparator)
			.append(backpointer);
		
		if(containsAjacentNodes()){
			sb.append(fieldSeparator)
				.append(StringUtils.join(getAdjacentNodeNames(), fieldSeparator));
		}
		return sb.toString();
	}
	
	public static Node fromMR(String value){
		String[] parts = StringUtils.splitPreserveAllTokens(value, fieldSeparator);
		
		Node node = new Node()
			.setDistance(Integer.valueOf(parts[0]))
			.setBackpointer(StringUtils.trimToNull(parts[1]));
		
		if(parts.length > 2){
			node.setAdjacentNodeNames(Arrays.copyOfRange(parts, 2, parts.length));
		}
		return node;
	}
}

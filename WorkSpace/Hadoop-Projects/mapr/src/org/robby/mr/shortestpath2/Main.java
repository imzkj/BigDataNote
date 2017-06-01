package org.robby.mr.shortestpath2;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.commons.io.*;
import org.apache.commons.lang.*;

import redis.clients.jedis.Jedis;

import java.io.*;

public class Main {

	public static final String TARGET_NODE = "shortestpath.targetnode";
	
	public static void createInputFile(Path file) throws Exception{
		Configuration conf = new Configuration();
		FileSystem fs = file.getFileSystem(conf);
		
		Jedis jedis = new Jedis("192.168.1.121");
		LineIterator iter = IOUtils.lineIterator(fs.open(file), "UTF8");
		while(iter.hasNext()){
			String line = iter.nextLine();
			
			String[] parts = StringUtils.split(line);
			
		    jedis.lpush("all_nodes", parts[0]);
			String nodeName = "node_" + parts[0];
			System.out.println("length" + parts.length);
			if(parts.length > 1){
				for(int i=1; i<parts.length; i++){
					jedis.lpush(nodeName, parts[i]);
				}
			}
		}
		jedis.disconnect();
	}
	
	public static boolean findShortestPath(Path inputPath, Path outputPath) throws IOException, InterruptedException, ClassNotFoundException{
		Configuration conf = new Configuration();
	
		Job job = new Job(conf);
		
		job.setJarByClass(Main.class);
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.setInputPaths(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		
		job.waitForCompletion(true);

		return false;
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String inputFile = args[1];

		
		System.out.println("arg0=" + args[0]);
		System.out.println("arg1=" + args[1]);
		

		createInputFile(new Path(inputFile));
		findShortestPath(new Path("/input/"), new Path("/output/"));
	}

}

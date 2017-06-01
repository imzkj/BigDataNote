package org.robby.mr.friend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import redis.clients.jedis.Jedis;

public class Friends extends Configured implements Tool {

	public static class Map extends
			Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			// 192.168.1.101 - - [23/Jan/2013:22:07:21 +0800]
			// "GET /hadoop2/suggestion/sug.jsp?query=hello HTTP/1.1" 200 1294
			String line = value.toString().trim();

			//get friends
		    String f_key = "fri_" + line;
		    Jedis jedis = new Jedis("192.168.1.121");
		    List<String> my_friends = jedis.lrange(f_key, 0, -1);
		    HashMap<String, Integer> map_friend = new HashMap<String, Integer>();
		    for(String t:my_friends){
		    	map_friend.put(t, 1);
		    }
		    
		    for(String t:my_friends){
		    	String ff_key = "fri_" + t;
		    	List<String> f_friends = jedis.lrange(ff_key, 0, -1);
		    	
		    	System.out.println(t);
		    	
		    	for(String t1:f_friends){
		    		Integer n = map_friend.get(t1); 
		    		if(n == null && !line.equals(t1)){
		    	    	String word = line + ":" + t1;
		    	    	System.out.println(word);
		    	    	Text out = new Text(word);
		    	    	context.write(out, one);
		    	    }
		    	}
		    }
		    jedis.disconnect();
		}  
	}

	public static class Reduce extends
			Reducer<Text, IntWritable, Text, IntWritable> {

		public void reduce(Text key, Iterator<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum = 0;
			while (values.hasNext()) {
				sum += values.next().get();
			}

			context.write(key, new IntWritable(sum));
		}
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int ret = ToolRunner.run(new Friends() , args);
		System.exit(ret);
	}

	@Override
	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Configuration conf = getConf();

		Job job = new Job(conf, "Load Redis");

		job.setJarByClass(Friends.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(RedisOutputFormat.class);
		//job.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		return job.waitForCompletion(true) ? 0 : 1;
	}

}

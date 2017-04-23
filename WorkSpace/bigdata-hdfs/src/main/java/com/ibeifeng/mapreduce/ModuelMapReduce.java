package com.ibeifeng.mapreduce;

import java.io.IOException;

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
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ModuelMapReduce extends Configured implements Tool {

	// step 1: Mapper
	public static class ModuelMapper extends
			Mapper<LongWritable, Text, Text, IntWritable> {

		@Override
		public void setup(Context context) throws IOException,
				InterruptedException {
		}

		@Override
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

		}

		@Override
		public void cleanup(Context context) throws IOException,
				InterruptedException {
		}

	}

	// step 2: Reducer
	public static class ModuelReducer extends
			Reducer<Text, IntWritable, Text, IntWritable> {

		@Override
		protected void setup(Context context) throws IOException,
				InterruptedException {
		}

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
		}

		@Override
		protected void cleanup(Context context) throws IOException,
				InterruptedException {
		}

	}

	/**
	 * Execute the command with the given arguments.
	 * 
	 * @param args
	 *            command specific arguments.
	 * @return exit code.
	 * @throws Exception
	 */
	// int run(String [] args) throws Exception;

	// step 3: Driver
	public int run(String[] args) throws Exception {

		Configuration configuration = this.getConf();

		Job job = Job.getInstance(configuration, this.getClass()
				.getSimpleName());
		job.setJarByClass(ModuelMapReduce.class);

		// set job
		// input
		Path inpath = new Path(args[0]);
		FileInputFormat.addInputPath(job, inpath);

		// output
		Path outPath = new Path(args[1]);
		FileOutputFormat.setOutputPath(job, outPath);

		// Mapper
		job.setMapperClass(ModuelMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		// ============shuffle=================
		// 1.partitioner
		// job.setPartitionerClass(cls);

		// 2.sort
		// job.setSortComparatorClass(cls);

		// 3.group
		// job.setGroupingComparatorClass(cls);

		// job.setCombinerClass(class);

		// ============shuffle=================

		// Reducer
		job.setReducerClass(ModuelReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// submit job -> YARN
		boolean isSuccess = job.waitForCompletion(true);
		return isSuccess ? 0 : 1;

	}

	public static void main(String[] args) throws Exception {

		Configuration configuration = new Configuration();

		args = new String[] {
				"hdfs://hadoop-senior01.ibeifeng.com:8020/user/beifeng/wordcount/input",
				"hdfs://hadoop-senior01.ibeifeng.com:8020/user/beifeng/wordcount/output10" };

		// run job
		int status = ToolRunner.run(configuration, new ModuelMapReduce(), args);

		// exit program
		System.exit(status);
	}

}

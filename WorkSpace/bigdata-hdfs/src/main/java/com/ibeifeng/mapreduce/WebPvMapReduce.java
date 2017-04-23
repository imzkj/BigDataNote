package com.ibeifeng.mapreduce;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
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



public class WebPvMapReduce extends Configured implements Tool {

	// step 1: Mapper
	public static class WebPvMapper extends
			Mapper<LongWritable, Text, IntWritable, IntWritable> {
		private IntWritable mapOutputKey = new IntWritable();
		private IntWritable mapOutputValue = new IntWritable(1);

		@Override
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

			// line value
			String lineValue = value.toString();

			// spilt
			String[] values = lineValue.split("\t");

			// url
			String urlValue = values[1];

			if (StringUtils.isBlank(urlValue)) {
				// conuter
				context.getCounter("WEBPVMAPPER_CUUNTERS", "URL_BLANK")
						.increment(1L);
				return;
			}

			if (30 > values.length) {

				// conuter
				context.getCounter("WEBPVMAPPER_CUUNTERS", "LENGTH_LT_30")
						.increment(1L);

				return;
			}

			// province id
			String provinceIdValue = values[23];

			if (StringUtils.isBlank(provinceIdValue)) {
				// conuter
				context.getCounter("WEBPVMAPPER_CUUNTERS", "PROVINCEID_BLANK")
						.increment(1L);
				return;
			}

			Integer provinceId = Integer.MAX_VALUE;
			try {
				provinceId = Integer.valueOf(provinceIdValue);
			} catch (Exception e) {
				// conuter
				context.getCounter("WEBPVMAPPER_CUUNTERS",
						"PROVINCEID_NOT_NUMBER").increment(1L);
				return;
			}

			// map outpu key
			mapOutputKey.set(provinceId);

			context.write(mapOutputKey, mapOutputValue);
		}
	}

	// step 2: Reducer
	public static class WebPvReducer extends
			Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
		private IntWritable outputValue = new IntWritable();

		@Override
		protected void reduce(IntWritable key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			// temp sum
			int sum = 0;

			// iterator
			for (IntWritable value : values) {
				sum += value.get();
			}

			// set output
			outputValue.set(sum);

			context.write(key, outputValue);

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
		job.setJarByClass(WebPvMapReduce.class);

		// set job
		// input
		Path inpath = new Path(args[0]);
		FileInputFormat.addInputPath(job, inpath);

		// output
		Path outPath = new Path(args[1]);
		FileOutputFormat.setOutputPath(job, outPath);

		// Mapper
		job.setMapperClass(WebPvMapper.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);

		// ============shuffle=================
		// 1.partitioner
		// job.setPartitionerClass(cls);

		// 2.sort
		// job.setSortComparatorClass(cls);

		// 3.group
		// job.setGroupingComparatorClass(cls);

		// job.setCombinerClass(WCCombiner.class);

		// ============shuffle=================

		// Reducer
		job.setReducerClass(WebPvReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);

		// submit job -> YARN
		boolean isSuccess = job.waitForCompletion(true);
		return isSuccess ? 0 : 1;

	}

	public static void main(String[] args) throws Exception {

		Configuration configuration = new Configuration();

		args = new String[] {
				"hdfs://hadoop-senior01.ibeifeng.com:8020/user/beifeng/wordcount/input",
				"hdfs://hadoop-senior01.ibeifeng.com:8020/user/beifeng/wordcount/output13" };

		/*
		 * // run job int status = new WCMapReduce().run(args);
		 * 
		 * System.exit(status);
		 */
		// run job
		int status = ToolRunner.run(configuration, new WebPvMapReduce(), args);

		// exit program
		System.exit(status);
	}

}

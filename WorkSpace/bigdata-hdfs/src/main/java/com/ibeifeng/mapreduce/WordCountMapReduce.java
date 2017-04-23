package com.ibeifeng.mapreduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountMapReduce {

	// step 1: Mapper
	public static class WordCountMapper extends
			Mapper<LongWritable, Text, Text, IntWritable> {
		private Text mapOutputKey = new Text();
		private IntWritable mapOutputValue = new IntWritable(1);

		@Override
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

			// line value
			String lineValue = value.toString();

			// spilt
			// String[] strs = lineValue.split(" ");
			StringTokenizer stringTokenizer = new StringTokenizer(lineValue);
			while (stringTokenizer.hasMoreTokens()) {
				// set map output key
				mapOutputKey.set(stringTokenizer.nextToken());

				// output
				context.write(mapOutputKey, mapOutputValue);
			}

			/**
			 * // iterator for (String str : strs) {
			 * 
			 * mapOutputKey.set(str);
			 * 
			 * context.write(mapOutputKey, mapOutputValue);
			 * 
			 * }
			 */
		}

	}

	// step 2: Reducer
	public static class WordCountReducer extends
			Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable outputValue = new IntWritable();

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
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

	// step 3: Driver
	public int run(String[] args) throws Exception {

		Configuration configuration = new Configuration();

		Job job = Job.getInstance(configuration, this.getClass()
				.getSimpleName());
		job.setJarByClass(WordCountMapReduce.class);

		// set job
		// input
		Path inpath = new Path(args[0]);
		FileInputFormat.addInputPath(job, inpath);

		// output
		Path outPath = new Path(args[1]);
		FileOutputFormat.setOutputPath(job, outPath);

		// Mapper
		job.setMapperClass(WordCountMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		// Reducer
		job.setReducerClass(WordCountReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// submit job -> YARN
		boolean isSuccess = job.waitForCompletion(true);
		return isSuccess ? 0 : 1;

	}

	public static void main(String[] args) throws Exception {

		args = new String[] {
				"hdfs://hadoop-senior01.ibeifeng.com:8020/user/beifeng/wordcount/input",
				"hdfs://hadoop-senior01.ibeifeng.com:8020/user/beifeng/wordcount/output6" };

		// run job
		int status = new WordCountMapReduce().run(args);

		System.exit(status);
	}
}

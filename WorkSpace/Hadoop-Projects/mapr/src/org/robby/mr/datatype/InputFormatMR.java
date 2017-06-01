package org.robby.mr.datatype;



import java.io.IOException;
import java.util.Iterator;

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

public class InputFormatMR extends Configured implements Tool {

	public static class Map extends
			Mapper<Text, SmCdr2, Text, SmCdr2> {
		private final static IntWritable one = new IntWritable(1);

		@Override
		protected void map(Text key, SmCdr2 value, Context context)
				throws IOException, InterruptedException {
			// 192.168.1.101 - - [23/Jan/2013:22:07:21 +0800]
			// "GET /hadoop2/suggestion/sug.jsp?query=hello HTTP/1.1" 200 1294
			
			context.write(key, value);
		}
	}

	public static class Reduce extends
			Reducer<Text, SmCdr, SmCdr, SmCdr> {

		public void reduce(Text key, Iterator<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum = 0;
			while (values.hasNext()) {
				sum += values.next().get();
			}

			//context.write(key, new IntWritable(sum));
		}
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int ret = ToolRunner.run(new InputFormatMR() , args);
		System.exit(ret);
	}
 
	@Override
	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Configuration conf = getConf();

		Job job = new Job(conf, "Load Redis");

		job.setJarByClass(InputFormatMR.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(SmCdr2.class);

		job.setMapperClass(Map.class);
		//job.setReducerClass(Reduce.class);

		job.setInputFormatClass(SmCdrInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		SmCdrInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job,  new Path(args[1]));

		return job.waitForCompletion(true) ? 0 : 1;
	}

}

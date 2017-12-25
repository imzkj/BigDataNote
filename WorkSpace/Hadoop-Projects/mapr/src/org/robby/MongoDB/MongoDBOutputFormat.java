package org.robby.MongoDB;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;

import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

/**
 * 自定义outputformat
 * @author gerry
 *
 * @param <V>
 */
public class MongoDBOutputFormat<V extends MongoDBWritable> extends OutputFormat<NullWritable, V> {

	/**
	 * 自定义mongodb outputformat
	 * @author gerry
	 *
	 * @param <V>
	 */
	static class MongoDBRecordWriter<V extends MongoDBWritable> extends RecordWriter<NullWritable, V> {
		private DBCollection dbCollection = null;

		public MongoDBRecordWriter() {
		}
		
		public MongoDBRecordWriter(TaskAttemptContext context) throws IOException {
			DB db = Mongo.connect(new DBAddress("127.0.0.1", "hadoop"));
			dbCollection = db.getCollection("result");
		}

		@Override
		public void write(NullWritable key, V value) throws IOException, InterruptedException {
			value.write(this.dbCollection);
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			// 没有关闭方法
		}
	}

	@Override
	public RecordWriter<NullWritable, V> getRecordWriter(TaskAttemptContext context)
			throws IOException, InterruptedException {
		return new MongoDBRecordWriter<>(context);
	}

	@Override
	public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {
	}

	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
		return new FileOutputCommitter(null, context);
	}

}

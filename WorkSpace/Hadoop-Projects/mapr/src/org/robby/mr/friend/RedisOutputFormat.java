package org.robby.mr.friend;

import java.io.IOException;

import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import redis.clients.jedis.Jedis;


public class RedisOutputFormat<K, V> extends FileOutputFormat<K, V> {

	protected static class RedisRecordWriter<K, V> extends RecordWriter<K, V>{
		private Jedis jedis;
		
		RedisRecordWriter(Jedis jedis){
			this.jedis = jedis;
		}
		@Override
		public void close(TaskAttemptContext arg0) throws IOException,
				InterruptedException {
			// TODO Auto-generated method stub
			jedis.disconnect();
		}

		@Override
		public void write(K key, V value) throws IOException,
				InterruptedException {
			// TODO Auto-generated method stub
			boolean nullkey = key == null;
			boolean nullvalue = value == null;
			
			if(nullkey || nullvalue){
				return;
			}
			
			String[] s = key.toString().split(":");
			int score = Integer.parseInt(value.toString());
		    
			String k = "ref_" + s[0];
			jedis.zadd(k, score, s[1]);
			
		}
	}
	@Override
	public RecordWriter<K, V> getRecordWriter(TaskAttemptContext arg0)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return new RedisRecordWriter<K, V>(new Jedis("127.0.0.1"));
	}
}

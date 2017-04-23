package com.ibeifeng.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class FirstPartitioner extends Partitioner<PairWritable, IntWritable> {

	@Override
	public int getPartition(PairWritable key, IntWritable value,
			int numPartitions) {
		//key.getFirst().hashCode()的值可能超出int可表示的最大值，发生溢出最终
		// 得到的值为负数，因此与int最大值进行逻辑与运算避免溢出，
		// 对numPartitions（分区个数）进行模运算计算出该Map结果属于哪个分区
		return (key.getFirst().hashCode() & Integer.MAX_VALUE) % numPartitions;
	}

}

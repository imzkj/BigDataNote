package com.ibeifeng.mapreduce;

import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.WritableComparator;

public class FirstGroupingComparator implements RawComparator<PairWritable> {

	public int compare(PairWritable o1, PairWritable o2) {
		//第一个属性为实际的key，只需比较第一个属性即可
		return o1.getFirst().compareTo(o2.getFirst());
	}

	public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
		//起始位置s1、s2为0，因为第二个属性second为int类型长度为4个字节，长度l1、l2减去4后的
		// 长度即为取出第一个属性
		return WritableComparator.compareBytes(b1, 0, l1 - 4, b2, 0, l2 - 4);
	}

}

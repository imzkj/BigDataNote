package org.robby.mr.datatype;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class SmCdr implements WritableComparable  {
	public String a;
	public String b;
	public String c;

	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		String line = arg0.readLine();
		String arr[] = line.split(",");
		a = arr[0];
		b = arr[1];
		c = arr[2];
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		String l = a + "," + b + "," + c + "\n";
		arg0.writeBytes(l);
	}
	
	public String toString(){
		String l = a + "," + b + "," + c;
		return l;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		
		SmCdr sm = (SmCdr)o;
		return a.compareTo(sm.a);
	}

}
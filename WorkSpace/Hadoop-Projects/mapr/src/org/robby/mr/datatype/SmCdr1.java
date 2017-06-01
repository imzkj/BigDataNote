package org.robby.mr.datatype;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class SmCdr1 implements WritableComparable  {
	public String a;
	public String b;
	public String c;

	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		a = arg0.readUTF();
		b = arg0.readUTF();
		c = arg0.readUTF();
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		arg0.writeUTF(a);
		arg0.writeUTF(b);
		arg0.writeUTF(c);
	}
	
	public String toString(){
		String l = a + "," + b + "," + c;
		return l;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		
		SmCdr1 sm = (SmCdr1)o;
		return a.compareTo(sm.a);
	}

}
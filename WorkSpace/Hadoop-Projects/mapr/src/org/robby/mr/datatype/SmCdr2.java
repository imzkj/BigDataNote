package org.robby.mr.datatype;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class SmCdr2 implements WritableComparable  {
	public String oaddr;
	public String daddr;
	public int ts;

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		oaddr = in.readUTF();
		daddr = in.readUTF();
		ts = in.readInt();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeUTF(oaddr);
		out.writeUTF(daddr);
		out.writeInt(ts);
	}
	
	public String toString(){
		String l = oaddr + "," + daddr + "," + ts;
		return l;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		
		SmCdr2 sm = (SmCdr2)o;
		return oaddr.compareTo(sm.oaddr);
	}

	public void parseFromLine(String l){
		String arr[] = l.split(",");
		oaddr = arr[0];
		daddr = arr[1];
		ts = Integer.parseInt(arr[2]);
	}
}
package org.robby.seri;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.hadoop.io.Writable;

public class HadoopSeri implements Writable{

	public long a,b,c;
	/**
	 * @param args
	 */
	
	public HadoopSeri(long a, long b, long c){
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public HadoopSeri() {
		// TODO Auto-generated constructor stub
		a = b = c = 0L;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		HadoopSeri a = new HadoopSeri(1L, 2L, 3L);
		
		FileOutputStream fos = new FileOutputStream("temp.out");
		DataOutputStream objOut = new DataOutputStream(fos);
		a.write(objOut);
		objOut.close();
		
		FileInputStream fin = new FileInputStream("temp.out");
		DataInputStream objIn = new DataInputStream(fin);
		HadoopSeri b = new HadoopSeri();
		b.readFields(objIn);
		objIn.close();
		System.out.println(b.a+","+b.b+","+b.c);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		this.a = in.readLong();
		this.b = in.readLong();
		this.c = in.readLong();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeLong(a);
		out.writeLong(b);
		out.writeLong(c);
	}
}

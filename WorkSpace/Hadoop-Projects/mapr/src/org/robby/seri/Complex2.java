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
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Writable;

public class Complex2 implements Writable{

	public long a;
	public Complex b;
	
	public Complex2(long a, Complex b){
		this.a = a;
		this.b = b;
	}
	
	public Complex2() {
		a = 0L;
		b = null;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		List<String> l = new ArrayList<String>();
		l.add("123");
		l.add("456");
		Complex a = new Complex(l);
		Complex2 a1 = new Complex2(1L, a);
		
		FileOutputStream fos = new FileOutputStream("temp.out");
		DataOutputStream objOut = new DataOutputStream(fos);
		a1.write(objOut);
		objOut.close();
		
		FileInputStream fin = new FileInputStream("temp.out");
		DataInputStream objIn = new DataInputStream(fin);
		Complex2 b = new Complex2();
		b.readFields(objIn);
		objIn.close();
		System.out.println(b.a);
		for(String s:b.b.l){
			System.out.println(s);
		}
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		a = in.readLong();
		if(b==null){
			b = new Complex();
		}
		b.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeLong(a);
		b.write(out);
	}
}

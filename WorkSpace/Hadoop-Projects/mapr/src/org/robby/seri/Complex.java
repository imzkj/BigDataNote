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

public class Complex implements Writable{

	public List<String> l;
	
	public Complex(List<String> l){
		this.l = l;
	}
	
	public Complex() {
		l = null;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		List<String> l = new ArrayList<String>();
		l.add("123");
		l.add("456");
		Complex a = new Complex(l);
		
		FileOutputStream fos = new FileOutputStream("temp.out");
		DataOutputStream objOut = new DataOutputStream(fos);
		a.write(objOut);
		objOut.close();
		
		FileInputStream fin = new FileInputStream("temp.out");
		DataInputStream objIn = new DataInputStream(fin);
		Complex b = new Complex();
		b.readFields(objIn);
		objIn.close();
		for(String s:b.l){
			System.out.println(s);
		}
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		int n = in.readInt();
		if(l==null){
			l = new ArrayList<String>();
		}
		for(int i=0; i<n; i++){
			l.add(in.readLine());
		}
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeInt(l.size());
		for(String s:l){
			out.writeBytes(s);
			out.writeBytes("\n");
		}
	}
}

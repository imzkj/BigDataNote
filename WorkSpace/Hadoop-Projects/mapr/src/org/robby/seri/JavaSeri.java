package org.robby.seri;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class JavaSeri implements Serializable{

	public long a,b,c;
	/**
	 * @param args
	 */
	
	public JavaSeri(long a, long b, long c){
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		JavaSeri a = new JavaSeri(1L, 2L, 3L);
		
		FileOutputStream fos = new FileOutputStream("temp.out");
		ObjectOutputStream objOut = new ObjectOutputStream(fos);
		objOut.writeObject(a);
		objOut.close();
	}

}

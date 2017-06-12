package org.robby.mr.cus;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class Person implements WritableComparable<Person>{
	String name;
	int age;
	
	public Person(String name, int age){
		this.name = name;
		this.age = age;
	}
	
	public Person(){
		name = "";
		age = 0;
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		name = in.readLine();
		age = in.readInt();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeBytes(name);
		out.writeBytes("\n");
		out.writeInt(age);
	}

	@Override
	public int compareTo(Person o) {
		// TODO Auto-generated method stub
		int cmp = this.name.compareTo(o.name);
		if(cmp != 0)
			return cmp;
		
		return (this.age<o.age ? -1 : (this.age == o.age ? 0 : 1)); 
	}

	@Override
	public String toString() {
		return name + "," + Integer.toString(age);
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Person))
			return false;
		
		Person p = (Person)o;
		return this.name.equals(p.name) && this.age==p.age;
	}
	
	@Override
	public int hashCode(){
		return this.name.hashCode() + age;
	}
}

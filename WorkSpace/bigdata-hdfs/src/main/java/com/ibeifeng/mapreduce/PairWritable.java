package com.ibeifeng.mapreduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class PairWritable implements WritableComparable<PairWritable> {

    private String first;
    private int second;

    public PairWritable() {

    }

    public PairWritable( String first, int second ) {
        this.set(first, second);
    }

    public void set( String first, int second ) {
        this.setFirst(first);
        this.setSecond(second);
    }

    public String getFirst() {
        return first;
    }

    public void setFirst( String first ) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond( int second ) {
        this.second = second;
    }

    public void write( DataOutput out ) throws IOException {
        out.writeUTF(first);
        out.writeInt(second);

    }

    public void readFields( DataInput in ) throws IOException {
        this.first = in.readUTF();
        this.second = in.readInt();

    }

    public int compareTo( PairWritable o ) {
        int comp = this.first.compareTo(o.getFirst());

        if (0 != comp) {
            return comp;
        }
        return Integer.valueOf(getSecond()).compareTo(
                Integer.valueOf(o.getSecond()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + second;
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PairWritable other = (PairWritable) obj;
        if (first == null) {
            if (other.first != null)
                return false;
        } else if (!first.equals(other.first))
            return false;
        if (second != other.second)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PairWritable [first=" + first + ", second=" + second + "]";
    }

}

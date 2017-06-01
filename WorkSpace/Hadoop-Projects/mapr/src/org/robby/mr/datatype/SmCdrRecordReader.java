package org.robby.mr.datatype;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

public class SmCdrRecordReader extends RecordReader<Text, SmCdr2> {

	private LineRecordReader lineReader;
	private LongWritable lineKey;
	private Text lineValue;

	private Text key;
	private SmCdr2 value;
	private boolean more;
	private Configuration conf;
	private DataInputStream dis;
	private BufferedReader br;


	public SmCdrRecordReader(){
		key = new Text();
		value = new SmCdr2();
		more = true;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Text getCurrentKey() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	public SmCdr2 getCurrentValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return more ? 0f : 100f;
	}

	@Override
	public void initialize(InputSplit arg0, TaskAttemptContext arg1)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		FileSplit split = (FileSplit) arg0;
        conf = arg1.getConfiguration(); 
        Path file = split.getPath();
        FileSystem fs = file.getFileSystem(conf);
         
        System.out.println("reading: " + file);

        // open the file
        dis = fs.open(split.getPath());

       
        br = new BufferedReader(new InputStreamReader(dis));
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String l = br.readLine();
		if(l == null){
			more = false;
			return false;
		}
		
		value.parseFromLine(l);
		key.set(value.oaddr);
        return true;
        
	}

}

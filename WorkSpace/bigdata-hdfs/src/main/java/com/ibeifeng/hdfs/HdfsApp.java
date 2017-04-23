package com.ibeifeng.hdfs;

import java.io.File;
import java.io.FileInputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HdfsApp {

	public static FileSystem getFileSystem() throws Exception {

		// creat configuration , default & site.xml
		Configuration configuration = new Configuration();
		// configuration.set("fs.defaultFS",
		// "hdfs://hadoop-senior01.ibeifeng.com:8020");

		// get filesystem
		FileSystem fileSystem = FileSystem.get(configuration);

		return fileSystem;
	}

	public static void read(String fileName) throws Exception {

		// String fileName = "/user/beifeng/temp/conf/core-site.xml";

		FileSystem fileSystem = getFileSystem();

		// read Path
		Path readPath = new Path(fileName);

		// input Stream
		FSDataInputStream inStream = fileSystem.open(readPath);

		try {
			IOUtils.copyBytes(inStream, System.out, 4096, false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(inStream);
		}

	}

	public static void main(String[] args) throws Exception {

		// read file
		// String fileName = "/user/beifeng/temp/conf/core-site.xml";
		// read(fileName);

		String fileName = "/user/beifeng/wc.input";

		FileSystem fileSystem = getFileSystem();

		// write Path
		Path writePath = new Path(fileName);

		// get input Stream
		FileInputStream inStream = new FileInputStream(new File(
				"/opt/datas/wc.input"));

		// write file
		FSDataOutputStream outStream = fileSystem.create(writePath);
		try {
			IOUtils.copyBytes(inStream, outStream, 4096, false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(inStream);
			IOUtils.closeStream(outStream);
		}

	}

}

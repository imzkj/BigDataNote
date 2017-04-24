package com.ibeifeng.hdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class HDFStest {

	public static FileSystem fs;
	private FileStatus fileStatus2;

	@Test
	public void testRead() throws IOException, URISyntaxException {
		// fs = FileSystem.get(new URI("hdfs://hadoop:9000"), new
		// Configuration());
		// fs = hdfs.getFilesSystem();
		Path path = new Path("/ddd/mp.txt");
		InputStream in = fs.open(path);
		IOUtils.copyBytes(in, System.out, 4096, false);
		IOUtils.closeStream(in);
		System.out.println("testread");
	}

	public void testUpload() throws IOException {
		FSDataOutputStream out = fs.create(new Path("/wor.txt"));
		FileInputStream in = new FileInputStream(new File("D:\test1.txt"));
		IOUtils.copyBytes(in, out, 4096, true);
		System.out.println("testupload");
	}

	@Test
	public void testPut() throws IOException {
		Path l = new Path("E:\\BaiduYunDownload\\大数据架构师Hadoop课程资料\\04.随堂资料\\orders.csv");
		Path y = new Path("/home/hadoop/testdata/join/input");
		fs.copyFromLocalFile(l, y);
	}

	@Test
	public void putMerge() throws IOException {
		LocalFileSystem local = FileSystem.getLocal(new Configuration());
		FileStatus[] listStatus = local.listStatus(new Path("E:/《汇编语言与计算机系统组成》机械工业出版社，课后习题答案"));
		FSDataOutputStream fsDataOutputStream = fs.create(new Path("/home/hebing.txt"));
		for (FileStatus fileStatus : listStatus) {
			Path path = fileStatus.getPath();
			String name = path.getName();
			System.out.println("文件为：" + name);
			InputStream open = local.open(path);
			byte[] b = new byte[1024];
			int len;
			while ((len = open.read(b)) > 0) {
				fsDataOutputStream.write(b, 0, len);
			}
			open.close();
		}
		fsDataOutputStream.close();
	}

	@Test
	public void getStatus() throws IOException {
		Path y = new Path("/input");
		fileStatus2 = fs.getFileStatus(y);
		BlockLocation[] fileBlockLocations = fs.getFileBlockLocations(fileStatus2, 0, fileStatus2.getLen());
		for (BlockLocation blockLocation : fileBlockLocations) {
			String[] hosts = blockLocation.getHosts();
			String[] names = blockLocation.getNames();
			for (String string : names) {
				System.err.println(string);
			}
			for (String string : hosts) {
				System.out.println(string);
			}
		}
	}

	@Test
	public void testCluster() throws IOException {
		DistributedFileSystem distributedFileSystem = (DistributedFileSystem) fs;
		DatanodeInfo[] dataNodeStats = distributedFileSystem.getDataNodeStats();
		for (DatanodeInfo datanodeInfo : dataNodeStats) {
			String hostName = datanodeInfo.getHostName();
			System.out.println(hostName);
		}
	}

	@Test
	public void testList() throws IOException, URISyntaxException {
		fs = FileSystem.get(new URI("hdfs://MyDream:9000"), new Configuration());
		// Configuration());
		// fs = hdfs.getFilesSystem();
		Path path = new Path("/home/hadoop");
		FileStatus[] listStatus = fs.listStatus(path);
		// fs.getFileBlockLocations(listStatus, 0, 10);
		for (FileStatus ls : listStatus) {
			Path p = ls.getPath();
			String info = ls.isDir() ? "文件" : "目录";
			System.out.println(info + ":" + p);

		}
	}

	@Test
	public void testMkdir() throws IOException {
		String path="/home/hadoop/testdata/join/input";
		boolean flag = fs.mkdirs(new Path(path));
		System.out.println(flag);
	}

	@Before
	public void init() throws IOException, URISyntaxException, InterruptedException {
		//fs = FileSystem.get(new URI("hdfs://master:8020"), new Configuration(), "root");
		 fs = FileSystem.get(new Configuration());
	}

	@Test
	public void testDel() throws IllegalArgumentException, IOException {
		boolean flag = fs.delete(new Path("/tem/tem.txt"), true);
		System.out.println(flag);
	}

	@Test
	public void testCreat() throws IOException {
		FSDataOutputStream create = fs.create(new Path("/topkeyinput"));
		create.writeBytes("afshkj132456");
		create.write("sdji".getBytes());
		create.close();
		System.out.println("testCreat");
	}

	public void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
		// TODO Auto-generated method stub
		init();
		testList();
		testRead();
		testDel();
		testCreat();
		testUpload();
	}

}

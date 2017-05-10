package storm.topology.test;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class TestHBase {
	
	public static void main(String[] args) {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.rootdir", "hdfs://hive-stu.ibeifeng.com:9000/hbase");
		conf.set("hbase.zookeeper.quorum", "hive-stu.ibeifeng.com:2181");
		HConnection hconn = null;
		try {
			hconn = HConnectionManager.createConnection(conf);
			
			HTableInterface htable = hconn.getTable(TableName.valueOf(Bytes.toBytes("wordcount")));
			
			Scan scan = new Scan();
			ResultScanner resultScanner = htable.getScanner(scan);
			Iterator<Result> results = resultScanner.iterator();
			while(results.hasNext()){
				Result r = results.next();
				Cell[] cells = r.rawCells();
				for(Cell cell : cells){
					String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
					String cf = Bytes.toString(CellUtil.cloneFamily(cell));
					String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
					String value = Bytes.toString(CellUtil.cloneValue(cell));
					System.out.println("rowkey="+rowkey+",cf="+cf+",qualifier="+qualifier+",value="+value);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(hconn != null)
				hconn.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}

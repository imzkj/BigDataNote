package storm.trident1;

import org.apache.thrift7.TException;

import backtype.storm.generated.DRPCExecutionException;
import backtype.storm.utils.DRPCClient;

public class WordCountDrpcClient {
	
	public static void main(String[] args) throws TException, DRPCExecutionException {
		
		DRPCClient client = 
				new DRPCClient("hive-stu.ibeifeng.com", 3772);
		
		String jsonStr = client.execute("drpcService", "hadoop mapreduce yarn");
		
		System.err.println(jsonStr);
		
	}

}

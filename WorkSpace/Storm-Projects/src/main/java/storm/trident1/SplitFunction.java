package storm.trident1;

import java.util.Map;

import backtype.storm.tuple.Values;
import storm.trident.operation.Function;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

public class SplitFunction implements Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5709475789218632706L;

	private int partitionIndex;
	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		// TODO Auto-generated method stub
		partitionIndex = context.getPartitionIndex();
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		
		String str = tuple.getStringByField("str");
		
		System.err.println("****** partitionIndex=" +this.partitionIndex +" , str=" + str);
		String[] words = str.split(" ");
		
		for(String word : words){
			collector.emit(new Values(word));
		}
		
	}

}

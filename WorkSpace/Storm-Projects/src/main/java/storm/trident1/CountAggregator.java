package storm.trident1;

import java.util.Map;

import storm.trident1.CountAggregator.CountState;

import backtype.storm.tuple.Values;
import storm.trident.operation.Aggregator;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

/**
 * 同一批次内各个分区的分组统计操作
 * @author ibeifeng
 *
 */
public class CountAggregator implements Aggregator<CountState> {
	
	// 内部类记录状态
	static class CountState{
		long count = 0L;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6553052409195528424L;

	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	// 初始化状态
	@Override
	public CountState init(Object batchId, TridentCollector collector) {
		// 
		return new CountState();
	}

	/**
	 * 同一批次内各个分区有多少Tuple，调用该方法多少次
	 */
	@Override
	public void aggregate(CountState val, TridentTuple tuple, TridentCollector collector) {
		// 
		long oldCount = val.count;
		
		long newCount = oldCount + 1;
		
		// 更新状态
		val.count =  newCount;
	}

	@Override
	public void complete(CountState val, TridentCollector collector) {
		// 
		collector.emit(new Values(val.count));
	}

	

}

package storm.trident2.orderprocess;

import java.util.Map;

import org.slf4j.Logger;

import backtype.storm.tuple.Values;
import storm.trident.operation.Aggregator;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

/**
 * 进行同一批次各个分区内局部统计
 * @author ibeifeng
 *
 */
public class SaleSum implements Aggregator<storm.trident2.orderprocess.SaleSumState> {
	
	private Logger logger  = org.slf4j.LoggerFactory.getLogger(SaleSum.class);
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6879728480425771684L;

	private int partitionIndex ;
	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		
		partitionIndex = context.getPartitionIndex();
		
		//System.err.println(partitionIndex);
		logger.debug("partitionIndex=" + partitionIndex);
		//logger.info(arg0);
		//logger.error(arg0);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public SaleSumState init(Object batchId, TridentCollector collector) {
		//初始化累计状态
		return new SaleSumState();
	}

	@Override
	public void aggregate(SaleSumState val, TridentTuple tuple, TridentCollector collector) {
		//获取原来的值
		double oldSaleSum = val.saleSum;
		 //获取要处理的值
		double price = tuple.getDoubleByField("price");
		//更新新的值
		double newSaleSum = oldSaleSum + price ;
		//将新的值更新到状态中
		val.saleSum = newSaleSum;
		
	}

	@Override
	public void complete(SaleSumState val, TridentCollector collector) {
		
//		System.err.println("SaleSum---> partitionIndex=" + this.partitionIndex 
//				+ ",saleSum=" + val.saleSum);
		//发送新的值
		collector.emit(new Values(val.saleSum));
		
	}


}

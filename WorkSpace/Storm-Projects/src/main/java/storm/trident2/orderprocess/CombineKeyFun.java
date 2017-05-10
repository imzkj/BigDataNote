package storm.trident2.orderprocess;

import java.util.Map;

import backtype.storm.tuple.Values;
import storm.trident.operation.Function;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

/**
 * 为了方便后面的统计结果存储，需要将多字段进行拼接
 * @author ibeifeng
 *
 */
public class CombineKeyFun implements Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5609985189737017086L;

	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		
		//"yyyyMMddHHStr","country","province","city"
		String yyyyMMddHHStr = tuple.getStringByField("yyyyMMddHHStr");
		
		String country = tuple.getStringByField("country");
		
		String province = tuple.getStringByField("province");
		
		String city = tuple.getStringByField("city");
		
		String newkey = city+"_"+province+"_"+country+"_"+yyyyMMddHHStr;
		
		collector.emit(new Values(newkey));

	}

}

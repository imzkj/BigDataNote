package storm.trident2.orderprocess;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import backtype.storm.tuple.Values;
import storm.trident.operation.Function;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;
/**
 * 订单解析Function
 * @author ibeifeng
 *
 */
public class OrderParseFunction implements Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8531306604648164614L;

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
		String orderRecord = tuple.getStringByField("str");
		
		if(orderRecord != null && !"".equals(orderRecord)){
			String[] orderDetails = orderRecord.replace("\"", "").split(" ");
			
			// "timestamp" "consumer" "productName" "price" "country" "province" "city"
			long timestamp = Long.valueOf(orderDetails[0]);
			
			Date date = new Date(timestamp);
			
			DateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
			String yyyyMMddStr = yyyyMMdd.format(date);
			
			DateFormat yyyyMMddHH = new SimpleDateFormat("yyyyMMddHH");
			String yyyyMMddHHStr = yyyyMMddHH.format(date);
			
			
			DateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyyMMddHHmm");
			String yyyyMMddHHmmStr = yyyyMMddHHmm.format(date);
			
			String consumer = orderDetails[1];
			String productName = orderDetails[2];
			double price = Double.valueOf(orderDetails[3]);
			String country = orderDetails[4];
			String province = orderDetails[5];
			String city = orderDetails[6];
			
			collector.emit(new Values(timestamp,yyyyMMddStr,yyyyMMddHHStr,yyyyMMddHHmmStr,consumer,productName,price,
					country,province,city));
		}

	}

}

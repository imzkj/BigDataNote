package storm.trident1.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
/**
 * Spout组件
 * @author ibeifeng
 *
 */
public class SentenceSpout implements IRichSpout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4453106718690624704L;
	
	/**
	 * Tuple数据 的发射器
	 */
	private SpoutOutputCollector collector;
	
	private Map<Object,Values> tuples;
	
	private Map<Object,Integer> retries;
	
	private static final int MAX_RETRY = 5;
	
	private static final String[] SENTENCES = new String[]{
			"hadoop spark storm storm",
			"storm hadoop spark",
			"flume kafka flume yarn"
	};
	
	

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {		
		this.collector = collector;		
		this.tuples = new HashMap<Object,Values>();
		this.retries = new HashMap<Object,Integer>();
	}

	@Override
	public void close() {


		// 对于资源的释放关闭，可以在该方法中实现

	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void nextTuple() {
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Random r = new Random();
		String sentence  = SENTENCES[r.nextInt(SENTENCES.length)];
		System.err.println("******* sentence=" + sentence);
		//collector.emit(new Values(sentence));
		Object mssageId = new Object();
		Values values = new Values(sentence);
		collector.emit(values,mssageId );
		// 添加到缓存中
		tuples.put(mssageId, values);
	}

	@Override
	public void ack(Object msgId) {
		System.out.println("&&&&&&&& 消息发射成功：" + msgId);
		// 确认发射成功，将tuple从缓存中移除
		tuples.remove(msgId);
	}

	@Override
	public void fail(Object msgId) {
		
		System.out.println("&&&&&&&& 消息发射失败：" + msgId);
		
		Integer hasRetries = retries.get(msgId);
		
		if(hasRetries == null) { 
			hasRetries = 0;
		}else if(hasRetries > MAX_RETRY){
			tuples.remove(msgId);
			retries.remove(msgId);
			return;
		}
		
		// 重试
		Values values = tuples.get(msgId);
		
		// 重新发射
		collector.emit(values,msgId );
		
		hasRetries ++ ;
		
		retries.put(msgId, hasRetries);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("str"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}

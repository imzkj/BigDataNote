package storm.trident1.test;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
/**
 * bolt组件
 * 切分语句获取单词
 * @author ibeifeng
 *
 */
public class SplitBolt implements IRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3965408885290321463L;
	
	// bolt的tuple发射器
	private OutputCollector collector;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		// Map stormConf 驱动类中config
		
		Object value = stormConf.get("aaa");
		System.err.println("value=" + value);
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		try {
			String str = input.getStringByField("str");
			
			//System.err.println(str);
			
			// 拆分语句
			String[] words = str.split(" ");
			
			for(String word:words){
				// 向后面的组件发射tuple
				//collector.emit(new Values(word));
				// 启用消息可靠性保障机制，需要锚定接收到tuple
				collector.emit(input,new Values(word));
			}
			// 确认处理结束
			collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
			// 处理失败
			collector.fail(input);
		}
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// 声明向后面组件发射的Tuple key是word
		declarer.declare(new Fields("word"));

	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}

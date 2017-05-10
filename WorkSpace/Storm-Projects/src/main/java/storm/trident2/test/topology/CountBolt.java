package com.ibeifeng.storm.test;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * 用于单词计数
 * @author ad
 *
 */
public class CountBolt implements IRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5434581787223453214L;

	private OutputCollector collector;
	
	private Map<String,Long> wordcounts;
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		this.wordcounts = new HashMap<String,Long>();
	}

	@Override
	public void execute(Tuple input) {
		
		String word = input.getStringByField("word");

		Long counts = 0L;
		if(wordcounts.containsKey(word)){
			counts = wordcounts.get(word);
		}
		counts += 1;
		
		wordcounts.put(word, counts);
		
		for(String w : wordcounts.keySet()){
			
			System.err.println("word=" + w +"---> counts=" + wordcounts.get(w));
			//collector.emit(new Values(w,wordcounts.get(w) + ""));
			collector.emit(input,new Values(w,wordcounts.get(w) + ""));
		}
		
		collector.ack(input);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word","count"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}

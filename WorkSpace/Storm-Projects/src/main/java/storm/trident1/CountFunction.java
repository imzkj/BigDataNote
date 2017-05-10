package storm.trident1;

import java.util.HashMap;
import java.util.Map;

import storm.trident.operation.Function;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

/**
 * 单词次数统计
 * @author ad
 *
 */
public class CountFunction implements Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8179050548485979154L;
	
	private Map<String,Long> wordcounts;
	private int partitionIndex;

	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		wordcounts = new HashMap<String,Long>();
		
		partitionIndex = context.getPartitionIndex();
		System.err.println("countFunction的分区编号：" + partitionIndex);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		
		String word = tuple.getStringByField("word");
		
		Long counts = 0L;
		
		if(wordcounts.containsKey(word)){
			counts = wordcounts.get(word);
		}
		
		counts += 1;
		
		wordcounts.put(word, counts);
		
		
		for(String w: wordcounts.keySet()){
			
			//System.err.println("partitionIndex="+ partitionIndex + "--->word=" + w + ",count=" + wordcounts.get(w));
		}

	}
}

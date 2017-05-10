package storm.trident1;

import java.util.Map;

import storm.trident.operation.Filter;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

/**
 * 测试过滤并打印Filter
 * @author ibeifeng
 *
 */
public class HasFlumeFilter implements Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6868959143417503368L;

	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}
	/**
	 * 实现是否将Tuple保留在Stream中的逻辑
	 */
	@Override
	public boolean isKeep(TridentTuple tuple) {
		
		String str = tuple.getStringByField("str");
		
		//System.err.println(str);
		
		if(str.contains("flume")) {
			System.err.println("&&&&& " + str);
			return true;
		}
		
//		String desc = tuple.getStringByField("describe");
//		
//		System.err.println(desc);
		
		return false;
	}

}

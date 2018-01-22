package FutureModel;

public class RealData implements Data {
	
	protected final String result;

	public RealData(String para) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			sb.append(para);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		result = sb.toString();
	}

	@Override
	public String getResult() {
		return result;
	}
	
	

}

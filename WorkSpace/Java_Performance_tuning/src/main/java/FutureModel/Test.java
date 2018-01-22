package FutureModel;

public class Test {
	public static void main(String[] args) {
		Client client = new Client();
		Data data = client.request("北风网");
		System.out.println("请求结束");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("最后获取真实数据"+data.getResult());
		
		
	}

}

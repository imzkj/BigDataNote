package special;

public class DBQueryProxy implements IDBQuery {
	private DBQuery real = null;

	@Override
	public String request() {
		//真正需要的时候 才创建真实的对象
		if(real==null) {
			real = new DBQuery();
		} else {


		}
		//在多线程的环境下 返回一个虚假的类
		return real.request();
	}

}

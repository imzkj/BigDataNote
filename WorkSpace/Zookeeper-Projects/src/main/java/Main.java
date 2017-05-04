package lock;

import java.net.InetAddress;

public class Main {

	/**
	 * @param args
	 * 控制不同进程使用某公共资源
	 * 
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		InetAddress address = InetAddress.getLocalHost();
		Lock lock = LockFactory.getLock("/root/test", address.toString());
		
		while(true)
		{
			if (lock == null) {
				//to do
				
			}
			else {
				Thread.sleep(60*1000);
			}
		}
		
		
	}

}

package com.lesson5;


import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class SetConfig {

	/**
	 * @param args
	 */
	public static String url = "192.168.1.107:2181";
	private final static String root = "/myConf" ;
	//   数据库连接  URL ,username  ,passwd
	private final static String UrlNode = root  + "/url";
	private final static String userNameNode = root  + "/username";
	private final static String passWdNode = root  + "/passwd";

	private final static String auth_type = "digest" ;
	private final static String auth_passwd = "password" ;
	
	private final static String URLString = "10.11.1.1" ;
	private final static String UserName = "username123" ;
	private final static String Passwd = "password123" ;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		ZooKeeper zk = new ZooKeeper(url, 3000, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				System.out.println("触发了事件：" + event.getType());
				// System.out.println("事件状态："+event.getState()) ;
			}

		});
		
		
		while (ZooKeeper.States.CONNECTED != zk.getState()) {
			Thread.sleep(3000);
		}
		zk.addAuthInfo(auth_type, auth_passwd.getBytes()) ;
		
//		zk.delete(UrlNode, -1) ;
//		zk.delete(userNameNode, -1) ;
//		zk.delete(passWdNode, -1) ;
//		zk.delete(root, -1) ;
		
		if (zk.exists(root, true) == null) {
			zk.create(root, "root".getBytes(), Ids.CREATOR_ALL_ACL,
					CreateMode.PERSISTENT);
		}
		if (zk.exists(UrlNode, true) == null) {
			zk.create(UrlNode, URLString.getBytes(), Ids.CREATOR_ALL_ACL,
					CreateMode.PERSISTENT);
		}
		if (zk.exists(userNameNode, true) == null) {
			zk.create(userNameNode, UserName.getBytes(), Ids.CREATOR_ALL_ACL,
					CreateMode.PERSISTENT);
		}
		if (zk.exists(passWdNode, true) == null) {
			zk.create(passWdNode, Passwd.getBytes(), Ids.CREATOR_ALL_ACL,
					CreateMode.PERSISTENT);
		}

		
		zk.close();
	}

}

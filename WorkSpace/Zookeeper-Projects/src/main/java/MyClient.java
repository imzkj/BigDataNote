package com.lesson5;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class MyClient implements Watcher {

	/**
	 * @param args
	 */
	public static String url = "192.168.1.107:2181";

	private final static String root = "/myConf" ;
	//   数据库连接  URL ,username  ,passwd
	private  String UrlNode = root  + "/url";
	private  String userNameNode = root  + "/username";
	private  String passWdNode = root  + "/passwd";
	
	
	public static String authType = "digest" ;
	public static String authPasswd = "password" ;
	
	private String uRLString ;
	private String username ;
	private String passwd ;
	
	ZooKeeper zk = null;
	
	public String getuRLString() {
		return uRLString;
	}
	public void setuRLString(String uRLString) {
		this.uRLString = uRLString;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public void initValue()
	{
		try {
			uRLString = new String(zk.getData(UrlNode, false, null)) ;
			username = new String(zk.getData(userNameNode, true, null)) ;
			passwd = new String(zk.getData(passWdNode, true, null)) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ZooKeeper getZK() throws Exception
	{
		zk = new ZooKeeper(url, 3000, this);
		zk.addAuthInfo(authType, authPasswd.getBytes());
		
		while (zk.getState() != ZooKeeper.States.CONNECTED) {
			Thread.sleep(3000);
		}
		return zk;
	}
	
	
	public static void main(String[] args) throws Exception {
		MyClient zkTest2 = new MyClient();
		ZooKeeper zk = zkTest2.getZK() ;
		zkTest2.initValue() ;
		int i=0;
		while(true)
		{
			System.out.println(zkTest2.getuRLString()) ;
			System.out.println(zkTest2.getUsername()) ;
			System.out.println(zkTest2.getPasswd()) ;
			System.out.println("-------------------------------------------");
			Thread.sleep(10000);
			i++ ;
			if(i==10)
			{
				break;
			}
		}

		zk.close();

	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		if (event.getType() == Watcher.Event.EventType.None) {
			System.out.println("连接服务器成功！");
		} else if (event.getType() == Watcher.Event.EventType.NodeCreated) {
			System.out.println("节点创建成功！");
		} else if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
			System.out.println("子节点创建更新成功！");
			//读取新的配置
			initValue() ;
			
		} else if (event.getType() == Watcher.Event.EventType.NodeDataChanged) {
			System.out.println("节点更新成功！");
			//读取新的配置
			initValue() ;
			
		} else if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
			System.out.println("节点删除成功！");
		}

	}

}

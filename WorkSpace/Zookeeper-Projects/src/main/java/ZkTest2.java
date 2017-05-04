package main.java;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class ZkTest2 implements Watcher {

	/**
	 * @param args
	 */
	public static String url = "192.168.58.28:2181";
	public static String root = "/zk";
	public static String child1 = "/zk/child1";

	public static String authType = "digest" ;
	public static String authPasswd = "passwd" ;
	public static String authPasswd2 = "passwd123" ;
	
	public static void main(String[] args) throws Exception {
		ZkTest2 zkTest2 = new ZkTest2();

		ZooKeeper zk = new ZooKeeper(url, 3000, zkTest2);
		zk.addAuthInfo(authType, authPasswd.getBytes());
		while (zk.getState() != ZooKeeper.States.CONNECTED) {
			Thread.sleep(3000);
		}
		
		
		String createPath = null;
		
		zk.delete(child1, -1);
		zk.delete(root, -1) ;
		
		if (zk.exists(root, false) == null) {
			createPath = zk.create(root, "root".getBytes(),
					Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println(createPath + " 被创建");
		}
		if (zk.exists(child1, false) == null) {
			createPath = zk.create(child1, "child1".getBytes(),
					Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println(createPath + " 被创建");
		}

		String rootDataString = new String(zk.getData(root, true, null));
		System.out.println(rootDataString);

		zk.setData(root, "rootUpdate1".getBytes(), -1);
		rootDataString = new String(zk.getData(root, true, null));
		System.out.println(rootDataString);
		System.out.println(zk.getChildren(root, true));

		System.out.println("----------------------");
		System.out.println(new String(zk.getData(child1, true, null)));
		// zk.exists(child1, false);
		zk.setData(child1, "child1Update1".getBytes(), -1);
		System.out.println(new String(zk.getData(child1, true, null)));
		// zk.setData(child1, "child1Update1".getBytes(), -1) ;

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
			System.out.println("子节点创建成功！");
			//读取新的配置
		} else if (event.getType() == Watcher.Event.EventType.NodeDataChanged) {
			System.out.println("节点更新成功！");
		} else if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
			System.out.println("节点删除成功！");
			//读取新的配置
		}

	}

}

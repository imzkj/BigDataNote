package main.java;

import org.apache.zookeeper.*;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZkTest {

    /**
     * @param args
     */
    public static String url = "192.168.58.28:2181";
    public static String root = "/zk";
    public static String child1 = "/zk/child1";

    public static void main( String[] args ) throws Exception {
        // TODO Auto-generated method stub

        ZooKeeper zk = new ZooKeeper(url, 3000, new Watcher() {

            @Override
            public void process( WatchedEvent event ) {
                // TODO Auto-generated method stub
                System.out.println("触发了事件：" + event.getType());
//				System.out.println("事件状态："+event.getState()) ;
            }

        });

        while(!"CONNECTED".equals(zk.getState().toString())) {
            Thread.sleep(3000);
        }

        if (zk.exists(root, true) == null) {
            zk.create(root, "root".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        if (zk.exists(child1, true) == null) {
            zk.create(child1, "child1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        }

        String rootDataString = new String(zk.getData(root, true, null));
        System.out.println(rootDataString);

        zk.setData(root, "rootUpdate1".getBytes(), -1);
        rootDataString = new String(zk.getData(root, true, null));
        System.out.println(rootDataString);
        System.out.println(zk.getChildren(root, true));

        System.out.println("----------------------");
        System.out.println(new String(zk.getData(child1, false, null)));
        zk.exists(child1, false);
        zk.setData(child1, "child1Update1".getBytes(), -1);
        System.out.println(new String(zk.getData(child1, true, null)));
//		zk.setData(child1, "child1Update1".getBytes(), -1) ;

        zk.close();

    }

}

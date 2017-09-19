package ZKJ;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;


public class FirstClient {

    private static Jedis jedis = new Jedis("localhost");

    public void testConn() {
        try {
            jedis.connect();
            jedis.ping();
            jedis.quit();
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        }
    }

    public void setTest() {
        try {
            //EXPIRE key seconds 为给定key设置生存时间。当key过期时，它会被自动删除。
            jedis.expire("messages", 10);//10秒过期
            //EXPIREAT EXPIREAT的作用和EXPIRE一样，都用于为key设置生存时间。不同在于EXPIREAT命令接受的时间参数是UNIX时间戳(unix timestamp)。

            for (int i = 0; i < 100; i++) {
                //jedis.set("key" + i, "value" + i);
                jedis.rpush("messages", "value" + i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTest() {
        try {
            for (int i = 0; i < 100; i++) {
                //System.out.println(jedis.get("key" + i));
                //System.out.println(jedis.lrange("messages",  i,  i));
                //处于阻塞等待状态
                System.out.println(jedis.blpop(0, "messages"));//接收了数据后，队列里就没有数据了
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void detTest(){
        jedis.del("messages");
    }

    public static void main(String[] args) {
        FirstClient client = new FirstClient();
        client.setTest();
        client.getTest();
        //client.detTest();
        System.exit(0);
    }

}
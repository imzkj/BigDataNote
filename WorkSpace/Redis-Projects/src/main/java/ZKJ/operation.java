package ZKJ;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class operation {
    @Test
    public void test() {
        Jedis connection = JedisUtil.getConnection();
        //选择数据库，默认是0
        connection.select(1);
        //connection.set("zkj", "billionsyear");
        String zkj = connection.get("zkj");
        System.out.println(zkj);
        //System.out.println(connection.select(10));
    }

    @Test
    public void testPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        JedisPool pool = new JedisPool(jedisPoolConfig, "localhost", 6379);

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set("pooledJedis", "hello jedis pool!");
            System.out.println(jedis.get("pooledJedis"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //还回pool中
            if (jedis != null) {
                jedis.close();
            }
        }
        pool.close();
    }
}

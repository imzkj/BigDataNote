package ZKJ;

import redis.clients.jedis.Jedis;

public class JedisUtil {
    private static Jedis jedis;

    public static Jedis getConnection() {
        jedis = new Jedis("localhost", 6379);
        return jedis;
    }

    public void set( String key, String value ) {
        jedis.set(key, value);
    }

    public String get( String key ) {
        return jedis.get(key);
    }

}

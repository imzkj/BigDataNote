package ZKJ;

import java.util.List;

import redis.clients.jedis.Jedis;

/**
 * 系统名称：XXXXXXXX(redis-test)<br>
 * 所属模块：XXXXXXXXXXXXXX<br>
 * 功能描述：XXXXXXXXXXXXXX<br>
 * 文件名：redis.test.RedisTest.java<br>
 * 版本信息：1.00<br>
 *
 * 开发部门：XXXXX<br>
 * 创建者： Administrator<br>
 * 创建时间：2014-12-2 上午11:51:30<br>
 * 修改者： Administrator<br>
 * 修改时间：2014-12-2 上午11:51:30<br>
 */

public class RedisTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379, 0);
        String value;
        // 1. k-v存储
        jedis.set("name", "helloword");
        value = jedis.get("name");
        System.out.println("1: " + value);
        // 2. 将新增的值添加到旧值后面
        jedis.append("name", " append new info,");
        value = jedis.get("name");
        System.out.println("2: " + value);
        // 3. 删除key对应的记录
        // jedis.del("name");
        // value = jedis.get("name");
        // System.out.println("3: " + value);
        // 4. 批量设值
        jedis.mset("name1", "minxr", "name2", "aaa");
        System.out.println("4: " + jedis.mget("name1", "name2"));
        // 5. 清空数据，所有的
        // System.out.println(jedis.flushDB());
        // 6. 截取value的值
        System.out.println("6: " + jedis.getrange("name", 1, 3));
        // 7.按通配符模糊查找符合条件的key
        System.out.println("7: " + jedis.keys("*na*"));
        // 8. 数据库大小
        System.out.println("8: " + jedis.dbSize());

        // 9. k-v存储，v表示的List(队列形式)   //先进先出
        jedis.del("messages");
        jedis.rpush("messages", "how");
        jedis.rpush("messages", "are");
        jedis.rpush("messages", "you");
        // 返回messages对应的集合长度
        System.out.println("9: " + jedis.llen("messages"));
        // 取数据，第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
        List<String> values = jedis.lrange("messages", 0, 1);
        System.out.println("9: " + values);

        // 10. k-v存储，v表示的List(堆栈形式)  //后进先出
        jedis.del("desc");
        jedis.lpush("desc", "1");
        jedis.lpush("desc", "2");
        jedis.lpush("desc", "3");
        jedis.lpush("desc", "4");
        // 数组长度
        System.out.println("10: " + jedis.llen("desc"));
        System.out.println("10: " + jedis.lrange("desc", 0, -1));

        // 11. 取出库中所有的key
        for (String a : jedis.keys("*")) {
            System.out.println("======================");
            System.out.println(a);
        }

    }

}
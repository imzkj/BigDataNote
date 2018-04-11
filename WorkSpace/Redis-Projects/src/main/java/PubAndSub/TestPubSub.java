package PubAndSub;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class TestPubSub extends JedisPubSub {

	//	监听到订阅频道接受到消息时的回调
	@Override
	public void onMessage(String channel, String message) {
		// TODO Auto-generated method stub
		System.out.println(channel + "," + message);
	}

	//	监听到订阅模式接受到消息时的回调
	@Override
	public void onPMessage(String pattern, String channel, String message) {
		// TODO Auto-generated method stub
		System.out.println(pattern + "," + channel + "," + message);

	}

	//	订阅频道时的回调
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub
		System.out.println("订阅频道时的回调");
		System.out.println("onSubscribe: channel[" + channel + "]," + "subscribedChannels[" + subscribedChannels + "]");
	}

	//	取消订阅频道时的回调
	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub
		System.out.println(
				"onUnsubscribe: channel[" + channel + "], " + "subscribedChannels[" + subscribedChannels + "]");
	}

	//	订阅频道模式时的回调
	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		System.out.println("onPUnsubscribe: pattern[" + pattern + "]," +

				"subscribedChannels[" + subscribedChannels + "]");

	}

	//	取消订阅模式时的回调
	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		System.out.println("onPSubscribe: pattern[" + pattern + "], " +

				"subscribedChannels[" + subscribedChannels + "]");

	}

	@Test
	public void pubsubjava() {
		// TODO Auto-generated method stub
		Jedis jr = null;
		try {
			jr = new Jedis("127.0.0.1", 6379, 0);// redis服务地址和端口号
//			jr.auth("wx950709");
			TestPubSub sp = new TestPubSub();
			// jr客户端配置监听两个channel
			jr.subscribe(sp, "news.share", "news.blog");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jr != null) {
				jr.disconnect();
			}
		}
	}
}
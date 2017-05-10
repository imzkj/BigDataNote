package storm.trident2.test;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * Kafka生产者
 * 
 * @author ibeifeng
 *
 */
public class KafkaProducer {
	
	/**
	 * 获取Kafka
	 * @param brokerList
	 * @return
	 */
	public Producer<String,String> getKafkaProducer(String brokerList){
		// 设置配置属性
		Properties props = new Properties();
		props.put("metadata.broker.list", brokerList);
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		// key.serializer.class默认为serializer.class
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		// 可选配置，如果不配置，则使用默认的partitioner
		props.put("partitioner.class", "com.ibeifeng.storm.test.KafkaPartitioner");
		// 触发acknowledgement机制，否则是fire and forget，可能会引起数据丢失
		// 值为0,1,-1,可以参考
		// http://kafka.apache.org/08/configuration.html
		props.put("request.required.acks", "1");
		ProducerConfig config = new ProducerConfig(props);

		// 创建producer
		Producer<String, String> producer = new Producer<String, String>(config);
		
		return producer;
	}
	
	/**
	 * 关闭kafka生产者
	 * @param producer
	 */
	public void close(Producer<String,String> producer){
		producer.close();
	}
	
	/**
	 * 发送数据到Kafka上
	 * @param producer
	 * @param data
	 */
	public void sendMassage(Producer<String,String> producer,KeyedMessage<String, String> data){
		producer.send(data);
	}
	
	/**
	 * 组装消息
	 * @param topic
	 * @param msgKey
	 * @param msgContent
	 * @return
	 */
	public KeyedMessage<String,String> 
		getKeyedMessage(String topic,String msgKey,String msgContent){
		KeyedMessage<String,String> data = new KeyedMessage<String,String>(topic,msgKey,msgContent);
		return data;
	}
}

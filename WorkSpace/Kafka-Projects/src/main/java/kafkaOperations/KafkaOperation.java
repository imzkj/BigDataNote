package kafkaOperations;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringEncoder;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by ZKJ on 2017/9/7.
 */
public class KafkaOperation {
    // getKeyValueData();方法为直接连接kafka方式
    // getNormalData();为连接zookeeper方式
    protected static final Logger logger = LoggerFactory.getLogger(KafkaOperation.class);

    public static Consumer<String, String> getConsumer(String groupid) {
        Properties props = new Properties();
        //kafka所在机器端口，可以指定kafka集群多个机器：192.168.58.28:9093,192.168.58.29:9093,192.168.58.30:9093
        //直接连接kafka集群，也可以连接zookeeper集群
        props.put("bootstrap.servers", "10.201.3.65:9093");
        //指定消费者所在组名，一个组内的成员有一个消费到了新数据另外的就都不会再消费
        props.put("group.id", groupid);
        props.put("enable.auto.commit", true);
        props.put("auto.commit.interval.ms", 1000);
        props.put("session.timeout.ms", 30000);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return new KafkaConsumer<String, String>(props);
    }

    //获取以keyvalue形式上传的数据
    public static void getKeyValueData() {
        logger.info("获取token线程启动！");
        Consumer<String, String> consumer = getConsumer("test123");
        //指定消费的topic
        consumer.subscribe(Arrays.asList("rtsync.bdl_sms_terminal_sign"));

        while (true) {
            //拉取kafka数据
            ConsumerRecords<String, String> records = consumer.poll(2000);
            if (records != null && records.count() > 0) {
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("key:" + record.key() + "value: " + record.value());
                }
            }
            try {
                Thread.sleep(1 * 1000);
            } catch (Exception e) {

            }
        }
    }

    //获取一般数据格式，也可以用来显示keyvalue形式的value
    public static void getNormalData() {
        Properties prop = new Properties();
        prop.put("zookeeper.connect", "10.201.3.46:2181,10.201.3.65:2181");
        prop.put("serializer.class", StringEncoder.class.getName());
        prop.put("metadata.broker.list", "10.201.3.46:9093,10.201.3.65:9093");
        prop.put("group.id", "group1");
        ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(new ConsumerConfig(prop));
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();

        topicCountMap.put("datateam.event.marketing", 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(topicCountMap);
        while (true){
            final KafkaStream<byte[], byte[]> kafkaStream = messageStreams.get("datateam.event.marketing").get(0);
            ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();
            while (iterator.hasNext()) {
                String msg = new String(iterator.next().message());
                System.out.println("收到消息：" + msg);
            }
        }
    }

    public static void main(String[] args) {
        //getKeyValueData();
        getNormalData();
    }
}

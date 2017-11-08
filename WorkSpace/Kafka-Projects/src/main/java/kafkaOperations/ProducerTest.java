package kafkaOperations;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Created by ZKJ on 2017/9/4.
 * 生产kafka数据
 */
public class ProducerTest {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Properties propsudid = new Properties();
        //props.put("bootstrap.servers", "xxx:9092,xxx:9092");
        propsudid.put("bootstrap.servers", "xxx:9093");
        propsudid.put("acks", "all");
        propsudid.put("retries", 0);
        propsudid.put("batch.size", 16384);
        propsudid.put("linger.ms", 1);
        propsudid.put("buffer.memory", 33554432);
        propsudid.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        propsudid.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Properties propstoken = new Properties();
        //props.put("bootstrap.servers", "xxx:9092,xxx:9092");
        propstoken.put("bootstrap.servers", "xxx:9093");
        propstoken.put("acks", "all");
        propstoken.put("retries", 0);
        propstoken.put("batch.size", 16384);
        propstoken.put("linger.ms", 1);
        propstoken.put("buffer.memory", 33554432);
        propstoken.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        propstoken.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        int count = 10;
        Producer<String, String> producerudid = new KafkaProducer<String, String>(propsudid);
        Producer<String, String> producertoken = new KafkaProducer<String, String>(propstoken);
        for (int i = 0; i < count; i++) {

            producerudid.send(new ProducerRecord<String, String>("datateam.event.marketing", "{\"event\":\"action\",\"udid\":\"123\",\"eventtime\":\"2017-09-05 11:38:04\"}", "1"));
            producerudid.send(new ProducerRecord<String, String>("datateam.event.marketing", "{\"event\":\"action\",\"udid\":\"321\",\"eventtime\":\"2017-09-05 11:38:04\"}", "2"));
            producerudid.send(new ProducerRecord<String, String>("datateam.event.marketing", "{\"event\":\"action\",\"udid\":\"deviceId-868773020059609\",\"eventtime\":\"2017-09-05 11:38:04\"}", "3"));
            producertoken.send(new ProducerRecord<String, String>("rtsc._sign", "1", "{\"rowData\":\"{\\\"fusname\\\":\\\"\\\",\\\"fid\\\":\\\"16374919037\\\",\\\"fsystemveion\\\":\\\"10.3.3\\\",\\\"fproductversion\\\":\\\"10.6.3\\\",\\\"fproductname\\\":\\\"MyeyPro\\\",\\\"fpartner\\\":\\\"\\\",\\\"flasodifytime\\\":\\\"2017-08-27 19:26:22\\\",\\\"fresolution\\\":\\\"\\\",\\\"fip\\\":\\\"32227\\\",\\\"fudid\\\":\\\"111\\\",\\\"fstatus\\\":\\\"0\\\",\\\"fplatrmstring\\\":\\\"iPhonePlus\\\",\\\"fsdkversion\\\":\\\"\\\",\\\"executetime\\\":153600,\\\"fmemory\\\":\\\"\\\",\\\"fcreatetime\\\":\\\"2015-09-15 07:19:55\\\",\\\"freceivenotify\\\":\\\"0\\\",\\\"fimei\\\":\\\"\\\",\\\"fmodel\\\":\\\"iPhone\\\",\\\"fsystemname\\\":\\\"iPhone OS\\\",\\\"ftoken\\\":\\\"jjj\\\",\\\"fdevicemodel\\\":\\\"iPhone7,1\\\"}\",\"schemaName\":\"message\",\"tableName\":\"t_terminal_sign\",\"topic\":\"rtsync.hjk\",\"type\":\"UPDATE\"}"));
            if (i % 10 == 0) {
                System.out.println("i:" + i);
            }
        }
        producerudid.flush();
        producerudid.close();

        producertoken.flush();
        producertoken.close();
    }
}
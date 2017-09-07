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
        //props.put("bootstrap.servers", "10.200.8.12:9092,10.200.8.13:9092");
        propsudid.put("bootstrap.servers", "10.201.3.65:9093");
        propsudid.put("acks", "all");
        propsudid.put("retries", 0);
        propsudid.put("batch.size", 16384);
        propsudid.put("linger.ms", 1);
        propsudid.put("buffer.memory", 33554432);
        propsudid.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        propsudid.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Properties propstoken = new Properties();
        //props.put("bootstrap.servers", "10.200.8.12:9092,10.200.8.13:9092");
        propstoken.put("bootstrap.servers", "10.201.3.65:9093");
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
            producertoken.send(new ProducerRecord<String, String>("rtsync.bdl_sms_terminal_sign", "1", "{\"rowData\":\"{\\\"fusername\\\":\\\"\\\",\\\"fid\\\":\\\"16374919037\\\",\\\"fsystemversion\\\":\\\"10.3.3\\\",\\\"fproductversion\\\":\\\"10.6.3\\\",\\\"fproductname\\\":\\\"MyMoneyPro\\\",\\\"fpartner\\\":\\\"\\\",\\\"flastmodifytime\\\":\\\"2017-08-27 19:26:22\\\",\\\"fresolution\\\":\\\"\\\",\\\"fip\\\":\\\"36.102.225.157\\\",\\\"fudid\\\":\\\"a3c4600f30f57d3f3a1a0de8f0b0206837b5786f8b9663aa20e8c6cf428409a56410c114d0d7405732f9c6fc35b5a3cc\\\",\\\"fstatus\\\":\\\"0\\\",\\\"fplatformstring\\\":\\\"iPhone 6 Plus\\\",\\\"fsdkversion\\\":\\\"\\\",\\\"executetime\\\":1503833182000,\\\"fmemory\\\":\\\"\\\",\\\"fcreatetime\\\":\\\"2015-09-15 07:19:55\\\",\\\"freceivenotify\\\":\\\"0\\\",\\\"fimei\\\":\\\"\\\",\\\"fmodel\\\":\\\"iPhone\\\",\\\"fsystemname\\\":\\\"iPhone OS\\\",\\\"ftoken\\\":\\\"cef056b545b529a5f487251acb831566687f1a1826a5bdeff8f4976ce6159cf9\\\",\\\"fdevicemodel\\\":\\\"iPhone7,1\\\"}\",\"schemaName\":\"message\",\"tableName\":\"t_terminal_sign\",\"topic\":\"rtsync.bdl_sms_terminal_sign\",\"type\":\"UPDATE\"}"));
            producertoken.send(new ProducerRecord<String, String>("rtsync.bdl_sms_terminal_sign", "2", "{\"rowData\":\"{\\\"fusername\\\":\\\"u_w8hwgkr\\\",\\\"fid\\\":\\\"22045913365\\\",\\\"fsystemversion\\\":\\\"10.3.3\\\",\\\"fproductversion\\\":\\\"7.7.8\\\",\\\"fproductname\\\":\\\"MyCard\\\",\\\"fpartner\\\":\\\"\\\",\\\"flastmodifytime\\\":\\\"2017-08-27 19:26:22\\\",\\\"fresolution\\\":\\\"\\\",\\\"fip\\\":\\\"111.22.117.211\\\",\\\"fudid\\\":\\\"62FCEA3A-DDE0-4D18-BCA9-235B261057EC\\\",\\\"fstatus\\\":\\\"0\\\",\\\"fplatformstring\\\":\\\"iPhone 6 Plus\\\",\\\"fsdkversion\\\":\\\"\\\",\\\"executetime\\\":1503833182000,\\\"fmemory\\\":\\\"\\\",\\\"fcreatetime\\\":\\\"2017-08-27 19:26:22\\\",\\\"freceivenotify\\\":\\\"0\\\",\\\"fimei\\\":\\\"\\\",\\\"fmodel\\\":\\\"iPhone\\\",\\\"fsystemname\\\":\\\"iPhone OS\\\",\\\"ftoken\\\":\\\"2af5a02696bd60117a20f326015325c902939e742f43ea2164f64daee7acd9ca\\\",\\\"fdevicemodel\\\":\\\"iPhone7,1\\\"}\",\"schemaName\":\"message\",\"tableName\":\"t_terminal_sign\",\"topic\":\"rtsync.bdl_sms_terminal_sign\",\"type\":\"INSERT\"}"));
            producertoken.send(new ProducerRecord<String, String>("rtsync.bdl_sms_terminal_sign", "3", "{\"rowData\":\"{\\\"fusername\\\":\\\"\\\",\\\"fid\\\":\\\"220369181960\\\",\\\"fsystemversion\\\":\\\"5.0\\\",\\\"fproductversion\\\":\\\"10.4.0.5\\\",\\\"fproductname\\\":\\\"MyMoney For Meizu\\\",\\\"fpartner\\\":\\\"feidee\\\",\\\"flastmodifytime\\\":\\\"2017-08-27 19:26:12\\\",\\\"fresolution\\\":\\\"1920x1080\\\",\\\"fip\\\":\\\"222.135.112.116\\\",\\\"fudid\\\":\\\"deviceId-868773020059609\\\",\\\"fstatus\\\":\\\"0\\\",\\\"fplatformstring\\\":\\\"\\\",\\\"fsdkversion\\\":\\\"21\\\",\\\"executetime\\\":1503833171000,\\\"fmemory\\\":\\\"2.77 GB\\\",\\\"fcreatetime\\\":\\\"2017-08-26 14:38:10\\\",\\\"freceivenotify\\\":\\\"0\\\",\\\"fimei\\\":\\\"359786055559083\\\",\\\"fmodel\\\":\\\"SM-N9006\\\",\\\"fsystemname\\\":\\\"android OS\\\",\\\"ftoken\\\":\\\"mzZ496e0608715e64450302044c7c0f014a796700057151\\\",\\\"fdevicemodel\\\":\\\"\\\"}\",\"schemaName\":\"message\",\"tableName\":\"t_terminal_sign\",\"topic\":\"rtsync.bdl_sms_terminal_sign\",\"type\":\"UPDATE\"}"));

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
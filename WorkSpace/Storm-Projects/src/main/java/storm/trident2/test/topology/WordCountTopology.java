package storm.trident2.test.topology;

import java.util.Map;
import java.util.UUID;

import org.apache.storm.hbase.bolt.HBaseBolt;
import org.apache.storm.hbase.bolt.mapper.HBaseMapper;
import org.apache.storm.hbase.bolt.mapper.SimpleHBaseMapper;

import com.google.common.collect.Maps;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;

/**
 * 驱动类
 * 
 * 构造Topology
 * 
 * @author ibeifeng
 *
 */
public class WordCountTopology {
	
	private static final String SPOUT_ID = "kafkaSpout";
	
	private static final String SPLIT_BOLT = "splitBolt";
	
	private static final String COUNT_BOLT = "countBolt";
	
	private static final String HBASE_BOLT = "hbaseBolt";
	
	public static void main(String[] args) {
		
		
		TopologyBuilder builder = new TopologyBuilder();
		
		// 指定zookeeper节点
		BrokerHosts hosts = new ZkHosts("hive-stu.ibeifeng.com:2181");
		String topic = "test";
		String zkRoot  = "/" + topic;
		SpoutConfig spoutConf = new SpoutConfig(hosts,topic,zkRoot, UUID.randomUUID().toString());
		spoutConf.forceFromStart = false;
		// 如何解析kafka队列上的数据：以字符串进行解析
		spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
		
		KafkaSpout kafkaSpout = new KafkaSpout(spoutConf);
		// 指定Topology的Spout组件
		//builder.setSpout(SPOUT_ID, kafkaSpout);
		builder.setSpout(SPOUT_ID, new SentenceSpout());
		// 指定用两个executor来执行spout
		//builder.setSpout(SPOUT_ID, new SentenceSpout(),2);
		// .shuffleGrouping(SPOUT_ID)  kafkaspout ---->  splitbolt
		
		//builder.setBolt(SPLIT_BOLT, new SplitBolt()).shuffleGrouping(SPOUT_ID);
		//builder.setBolt(SPLIT_BOLT, new SplitBolt(),3).shuffleGrouping(SPOUT_ID);
		// bolt有12个Task来运行，12task由3个executor执行
		builder.setBolt(SPLIT_BOLT, new SplitBolt(),2).setNumTasks(4).shuffleGrouping(SPOUT_ID);
		
		// 根据单词分组
		builder.setBolt(COUNT_BOLT, new CountBolt()).fieldsGrouping(SPLIT_BOLT, new Fields("word"));
		
		// HBase表信息
		String tableName = "wordcount";
		HBaseMapper mapper = new SimpleHBaseMapper()
					.withRowKeyField("word")
					.withColumnFamily("cf")
					.withColumnFields(new Fields("word","count"));
		
		//指定连接HBase集群的客户端参数
		Map<String,Object> hbaseOpts = Maps.newHashMap();
		hbaseOpts.put("hbase.rootdir", "hdfs://hive-stu.ibeifeng.com:9000");
		hbaseOpts.put("hbase.zookeeper.quorum","hive-stu.ibeifeng.com:2181");
		
		Config conf = new Config();
		conf.put("aaa", 123);
		conf.put("hbase.conf", hbaseOpts);
		
		conf.setNumWorkers(2);
		// 设置tuple的处理超时时间
		conf.setMessageTimeoutSecs(30);
		conf.setNumAckers(4);
		
		HBaseBolt hbaseBolt = new HBaseBolt(tableName,  mapper).withConfigKey("hbase.conf");
		
		builder.setBolt(HBASE_BOLT,hbaseBolt).globalGrouping(COUNT_BOLT);
		
		LocalCluster localCluster = new LocalCluster();
		
		localCluster.submitTopology("wordcountTopo", conf, builder.createTopology());
		
	}
}

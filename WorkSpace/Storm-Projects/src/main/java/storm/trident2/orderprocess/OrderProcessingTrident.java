package storm.trident2.orderprocess;

import org.apache.storm.hbase.trident.state.HBaseMapState;


import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.tuple.Fields;
import storm.kafka.BrokerHosts;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;
import storm.kafka.trident.OpaqueTridentKafkaSpout;
import storm.kafka.trident.TransactionalTridentKafkaSpout;
import storm.kafka.trident.TridentKafkaConfig;
import storm.trident.Stream;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.FilterNull;
import storm.trident.operation.builtin.MapGet;
import storm.trident.operation.builtin.Sum;
import storm.trident.state.OpaqueValue;
import storm.trident.state.StateFactory;
import storm.trident.testing.MemoryMapState;
import storm.trident1.PrintTestFilter;
import storm.trident1.SplitFunction1;

/**
 * 订单处理Topology
 *
 * @author ibeifeng
 */
public class OrderProcessingTrident {

    private static final String SPOUT_ID = "kafakaSpout";

    public static void main( String[] args ) {

        // 构造TridentTopology

        TridentTopology tridentTopology = new TridentTopology();

        // 使用KafkaSpout从kafka上读取消息
        BrokerHosts hosts = new ZkHosts("hive-stu.ibeifeng.com:2181");
        String topic = "test";
        TridentKafkaConfig config = new TridentKafkaConfig(hosts, topic);

        config.forceFromStart = false;
        config.scheme = new SchemeAsMultiScheme(new StringScheme());

        OpaqueTridentKafkaSpout opaqueTridentKafkaSpout =
                new OpaqueTridentKafkaSpout(config);
        TransactionalTridentKafkaSpout transactionalTridentKafkaSpout =
                new TransactionalTridentKafkaSpout(config);

        // {"str":"xxxxxxxx"}

        Stream stream = tridentTopology.newStream(SPOUT_ID, opaqueTridentKafkaSpout);

        //stream.each(new Fields("str"), new PrintTestFilter());

        // timestamp,yyyyMMddStr,yyyyMMddHHStr,yyyyMMddHHmmStr,consumer,productName,price,country,province,city
        Stream hasParseStream = stream.each(new Fields("str"), new OrderParseFunction(),
                new Fields("timestamp", "yyyyMMddStr", "yyyyMMddHHStr", "yyyyMMddHHmmStr",
                        "consumer", "productName", "price",
                        "country", "province", "city"))
//			.each(new Fields("str","timestamp","yyyyMMddStr","yyyyMMddHHStr",
//					"yyyyMMddHHmmStr",
//					"consumer","productName","price",
//					"country","province","city"), new PrintTestFilter())
                ;

        // 1、每天电商网站总销售额
        // 去掉用不到的keyvalue
        Stream partitionStatictisStream =
                hasParseStream.project(new Fields("yyyyMMddStr", "price"))
                        // 随机重分区
                        .shuffle()
                        .groupBy(new Fields("yyyyMMddStr"))
                        //聚合链作用    一次groupby要对多个集合操作起作用，下面的聚合操作都是对前面的groopby结果集进行操作
                        .chainedAgg()
                        // 统计同一批次内各分区中订单金额总和
                        //局部统计
                        .partitionAggregate(new Fields("price"), new SaleSum(),
                                new Fields("saleTotalAmtOfPartDay"))
                        // 统计同一个批次内各分区中的订单笔数之和
                        .partitionAggregate(new Count(), new Fields("numOrderOfPartDay"))
                        .chainEnd()
                        .parallelismHint(5)
                        .toStream();
        // 全局统计每天的销售额
        TridentState saleAmtState =
                partitionStatictisStream.groupBy(new Fields("yyyyMMddStr"))
                        //全局统计，对saleTotalAmtOfPartDay进行sum操作
                        .persistentAggregate(
                                new MemoryMapState.Factory(),
                                new Fields("saleTotalAmtOfPartDay"), new Sum(),
                                new Fields("saleGlobalAmtOfDay"));
//		saleAmtState.newValuesStream()
//		.each(new Fields("yyyyMMddStr","saleGlobalAmtOfDay"), new PrintTestFilter());
//		;
        // 全局统计每天的订单总笔数
        TridentState numOfSaleState =
                partitionStatictisStream.groupBy(new Fields("yyyyMMddStr"))
                        .persistentAggregate(new MemoryMapState.Factory(),
                                new Fields("numOrderOfPartDay"), new Sum(),
                                new Fields("numOrderGlobalOfDay"));

//		numOfSaleState.newValuesStream()
//		.each(new Fields("yyyyMMddStr","numOrderGlobalOfDay"), new PrintTestFilter());
//		state.newValuesStream()
//		.each(new Fields("yyyyMMddStr","saleGlobalAmtOfDay"), new PrintTestFilter());


        // 构造一个本地drpc服务
        LocalDRPC localDRPC = new LocalDRPC();
        tridentTopology.newDRPCStream("saleAmtOfDay", localDRPC)
                //　 key 名称一定是args
                //对localDRPC.execute方法传过来的参数进行解析
                .each(new Fields("args"), new SplitFunction1(), new Fields("requestDate"))
                //从状态saleAmtState中取出要查询的字段
                .stateQuery(saleAmtState, new Fields("requestDate"), new MapGet(),
                        new Fields("saleGlobalAmtOfDay1"))
                .project(new Fields("requestDate", "saleGlobalAmtOfDay1"))
                //过滤掉空值
                .each(new Fields("saleGlobalAmtOfDay1"), new FilterNull())
        ;

        tridentTopology.newDRPCStream("numOrderOfDay", localDRPC)
                .each(new Fields("args"), new SplitFunction1(), new Fields("requestDate"))
                .stateQuery(numOfSaleState, new Fields("requestDate"), new MapGet(),
                        new Fields("numOrderGlobalOfDay1"))
                .project(new Fields("requestDate", "numOrderGlobalOfDay1"))
                .each(new Fields("numOrderGlobalOfDay1"), new FilterNull())
        ;


        // 基于地域、时段（yyyyMMddHHStr）统计分析销售额、订单笔数
//		"timestamp","yyyyMMddStr","yyyyMMddHHStr","yyyyMMddHHmmStr",
//		"consumer","productName","price",
//		"country","province","city"

        @SuppressWarnings("rawtypes")
        HBaseMapState.Options<OpaqueValue> opts = new HBaseMapState.Options<OpaqueValue>();
        opts.tableName = "saleTotalAmtOfAddrAndHour";
        opts.columnFamily = "cf";
        opts.qualifier = "sTAOAAH";

        // create 'saleTotalAmtOfAddrAndHour',{ NAME => 'cf' , VERSIONS => 1000}
        StateFactory factory = HBaseMapState.opaque(opts);

        TridentState saleTotalAmtOfAddrAndHourState =
                hasParseStream.project(new Fields("yyyyMMddHHStr",
                        "price", "country", "province", "city"))
                        //拼接字段成为key
                        .each(new Fields("yyyyMMddHHStr", "country", "province", "city")
                                , new CombineKeyFun(), new Fields("addrAndHour"))
                        .project(new Fields("addrAndHour", "price"))
                        .groupBy(new Fields("addrAndHour"))
                        .persistentAggregate(factory, new Fields("price"),
                                new Sum(), new Fields("saleTotalAmtOfAddrAndHour"));


        saleTotalAmtOfAddrAndHourState.newValuesStream()
                .each(new Fields("addrAndHour",
                        "saleTotalAmtOfAddrAndHour"), new PrintTestFilter());


        tridentTopology.newDRPCStream("saleTotalAmtOfAddrAndHour", localDRPC)
                .each(new Fields("args"), new SplitFunction1(), new Fields("requestAddrAndHour"))
                .stateQuery(saleTotalAmtOfAddrAndHourState, new Fields("requestAddrAndHour"),
                        new MapGet(), new Fields("saleTotalAmtOfAddrAndHour"))
        //.project(new Fields("requestAddrAndHour","saleTotalAmtOfAddrAndHour"))
        //.each(new Fields("saleTotalAmtOfAddrAndHour"), new FilterNull())
        ;


        Config conf = new Config();


        if (args == null || args.length <= 0) {
            // 本地测试
            LocalCluster localCluster = new LocalCluster();
            // topology名称唯一
            localCluster.submitTopology("orderProcessingTrident", conf, tridentTopology.build());

            while(true) {

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String saleAmtResult =
                        localDRPC.execute("saleAmtOfDay", "20160828 20160827");

                System.err.println("saleAmtResult=" + saleAmtResult);

                String numberOrderResult =
                        localDRPC.execute("numOrderOfDay", "20160828 20160827");
                System.err.println("numberOrderResult=" + numberOrderResult);

                String saleTotalAmtOfAddrAndHourRessult =
                        localDRPC.execute("saleTotalAmtOfAddrAndHour", "苏州_江苏_中国_2016082815");

                System.err.println(saleTotalAmtOfAddrAndHourRessult);

            }
        } else {
            try {
                StormSubmitter.submitTopology(args[0], conf, tridentTopology.build());
            } catch (AlreadyAliveException e) {
                e.printStackTrace();
            } catch (InvalidTopologyException e) {
                e.printStackTrace();
            }
        }

    }

}

package com.ibeifeng.storm.trident;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.Sum;
import storm.trident.testing.FixedBatchSpout;
import storm.trident.testing.MemoryMapState;
import storm.trident1.CountAggregator;
import storm.trident1.PrintTestFilter;
import storm.trident1.SplitFunction;

/**
 * 通过Trident来实现词频统计
 *
 * @author ibeifeng
 */
public class WordCountTrident2 {

    private static final String SPOUT_ID = "testSpout";

    public static void main( String[] args ) {

        @SuppressWarnings("unchecked")
        FixedBatchSpout testSpout =
                new FixedBatchSpout(new Fields("str", "describe"), 5,
                        new Values("hadoop yarn storm", "hadoop is a famous tech"),
                        new Values("hadoop mapreduce storm", "jijijijg"),
                        new Values("hadoop flume flume storm", "jijisjjbbb"),
                        new Values("hadoop yarn storm", "jigjeisg"),
                        new Values("kafka yarn kafka", "sjigesg"),
                        new Values("spark yarn storm mahout", "ksjigje"));

        testSpout.setCycle(true);
        // 构造topology
        TridentTopology topology = new TridentTopology();

        // 构造DAG  Stream   指定数据采集器
        Stream stream = topology.newStream(SPOUT_ID, testSpout);
        // 指定Tuple中哪个keyvalue对进行 过滤操作
        stream
                //.each(new Fields("str"), new PrintTestFilter())
                // 链式调用
                //.each(new Fields("str"), new HasFlumeFilter())

                //.each(new Fields("str"), new PrintTestFilter())

                // 对tuple中key名称为str的keyvalue对的value值进行splitfunction操作，
                //产生新的keyvalue对key名称为word
                .each(new Fields("str"), new SplitFunction(), new Fields("word"))
                // 设置2个executor来执行splitfunction操作
                .parallelismHint(2)
                // tuple---> {"str":"xxxxxxxxx","describe":"xxx","word":"flume"}
                //.each(new Fields("str","describe","word"), new PrintTestFilter())

                // 指定Tuple中只保留 key名称为word的keyvalue对
                .project(new Fields("word"))

                // mapreduce分区

                //.groupBy(new Fields("word"))
                .partitionBy(new Fields("word"))
                .chainedAgg()
                // 同一批次分区内分组统计
                .partitionAggregate(new Fields("word"), new CountAggregator(), new Fields("count"))
                .chainEnd()
                .parallelismHint(3)
                //.each(new Fields("word","count"),new PrintTestFilter())

                .groupBy(new Fields("word"))
                //对count进行sum操作,new MemoryMapState.Factory()指定存储在内存中
                .persistentAggregate(new MemoryMapState.Factory(), new Fields("count"), new Sum(),
                        new Fields("globalCount"))
                .newValuesStream()
                .each(new Fields("word", "globalCount"), new PrintTestFilter())
        ;

        Config config = new Config();

        // 当args没有值，运行过程中没有指定参数
        if (args == null || args.length <= 0) {
            // 本地测试
            LocalCluster localCluter = new LocalCluster();

            localCluter.submitTopology("wordcountTrident", config, topology.build());
        } else {
            // 提交集群运行

            try {
                StormSubmitter.submitTopology(args[0], config, topology.build());
            } catch (AlreadyAliveException e) {
                e.printStackTrace();
            } catch (InvalidTopologyException e) {
                e.printStackTrace();
            }
        }

    }

}

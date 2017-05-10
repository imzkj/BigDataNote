package storm.trident1;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.testing.FixedBatchSpout;

/**
 * 通过Trident来实现词频统计
 *
 * @author ibeifeng
 */
public class WordCountTrident {

    private static final String SPOUT_ID = "testSpout";

    public static void main( String[] args ) {

        //测试spout
        // arg1向后面发送的key名称依次是什么
        //arg2多少条记录组装成一个批次
        //测试数据，测试spout随机从里面取出arg2个
        @SuppressWarnings("unchecked")
        FixedBatchSpout testSpout =
                new FixedBatchSpout(new Fields("str", "describe"), 5,
                        new Values("hadoop yarn storm", "hadoop is a famous tech"),
                        new Values("hadoop mapreduce storm", "jijijijg"),
                        new Values("hadoop flume flume storm", "jijisjjbbb"),
                        new Values("hadoop yarn storm", "jigjeisg"),
                        new Values("kafka yarn kafka", "sjigesg"),
                        new Values("spark yarn storm mahout", "ksjigje"));
        //TRUE会一直发送数据，FALSE会只发一次
        testSpout.setCycle(true);
        // 构造topology
        TridentTopology topology = new TridentTopology();

        // 构造DAG  Stream   指定数据采集器
        Stream stream = topology.newStream(SPOUT_ID, testSpout).parallelismHint(1);
        // 指定Tuple中哪个keyvalue对进行 过滤操作
        //stream.shuffle()
        //stream.global()
        //stream.batchGlobal()
        stream.broadcast()
                //.each(new Fields("str"), new PrintTestFilter())
                // 链式调用
                //.each(new Fields("str"), new HasFlumeFilter())

                //.each(new Fields("str"), new PrintTestFilter())

                // 对tuple中key名称为str的keyvalue对的value值进行splitfunction操作，
                //产生新的keyvalue对key名称为word
                .each(new Fields("str"), new SplitFunction(), new Fields("word"))
                // 设置2个executor来执行splitfunction操作
                .parallelismHint(3)
                // tuple---> {"str":"xxxxxxxxx","describe":"xxx","word":"flume"}
                //.each(new Fields("str","describe","word"), new PrintTestFilter())

                // 指定Tuple中只保留 key名称为word的keyvalue对
                .project(new Fields("word"))
                .partitionBy(new Fields("word"))
                //.groupBy(new Fields("word")).toStream()
                //.each(new Fields("word"), new  PrintTestFilter())
                //.parallelismHint(4)
                .each(new Fields("word"), new CountFunction(), new Fields("count"))
                .parallelismHint(3)
        //.each(new Fields("word","count"), new PrintTestFilter())
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

package com.ibeifeng.mapreduce.sort;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ibf on 2017/1/16.
 * 需求：获取每日各个省份UV
 *      UV:独立访客数
 *      数据：date_provice_guid
 *      代表某天某个省份某个人无论访问该网站多少次，仅仅记做一次访问统计
 *
 */
public class WebUVMapReduce extends Configured implements Tool{

    // step 1: Mapper
    public static class WebUvMapper extends
            Mapper<LongWritable, Text, Text, NullWritable> {

        private Text mapOutputKey = new Text();

        @Override
        public void setup(Context context) throws IOException, InterruptedException{
        }

        @Override
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {

            //获取每一行的内容
            String lineValue = value.toString();

            //分片切割
            String[] values = lineValue.split("\t");

            //判断数据长度
            if(30 > values.length){
                return;
            }

            //guid ID，获取用户GUID，并进行判断是否有值，若无值就过滤
            String guidIDValue = values[5];
            if(StringUtils.isBlank(guidIDValue)){
                return;
            }

            //track time，用户访问的时间进行判断
            String trackTimeValue = values[17];
            if(StringUtils.isBlank(trackTimeValue)){
                return;
            }

            //截取字符串
            String dateValue = trackTimeValue.substring(0,10);

            //proviceID，获取各个省份信息进行判断
            String proviceIDValue = values[23];
            if(StringUtils.isBlank(proviceIDValue)){
                return;
            }
            //优化过滤，将省份不是数字的过滤掉
            try {
                Integer.valueOf(proviceIDValue);
            }catch (Exception e){
                return;
            }

            //date_provice_guid，拼接字符串
            mapOutputKey.set(dateValue + "\t" + proviceIDValue + "_" + guidIDValue);

            //output，输出
            context.write(mapOutputKey,NullWritable.get());
        }

        @Override
        public void cleanup(Context context) throws IOException,
                InterruptedException {
        }

    }

    // step 2: Reducer
    public static class WebUvReducer extends
            Reducer<Text, NullWritable, Text, IntWritable> {
        //用户存储每天每个省份的uv数
        //声明Map，便于全局使用
        private Map<String,Integer> dateMap;
        //output key
        private Text outputKey = new Text();
        //output value
        private IntWritable outputValue = new IntWritable();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            //初始化Map
            dateMap = new HashMap<String, Integer>();
        }

        @Override
        protected void reduce(Text key, Iterable<NullWritable> values,
                              Context context) throws IOException, InterruptedException{
            //比如得到：2017-01-18\t29，获取key，进行分割
            String date = key.toString().split("_")[0];

            //进行判断，查看date_provice是否已经存在Map集合中
            if(dateMap.containsKey(date)){//如果存在，再获取UV，再进行加一
                //第一步：依据date_provice获取Map集合中的UV值
                Integer perviosUv = dateMap.get(date);
                //第二步：UV值加一
                Integer uv = perviosUv + 1;
                //第三步：更新Map集合中date_provice对应新的UV值
                dateMap.put(date,uv);
            }else{//如果不存在，说明第一次存储，UV值为1，格式<date_provice , 1>
                //update
                dateMap.put(date,1);
            }

            //注意，此处不能进行输出，必须等reduce Task将所有的数据处理完以后，才能输出
        }

        //将Map集合结果进行输出
        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {

            //得到Map集合中所有key，date_provice
            Set<String> dateSet = dateMap.keySet();
            //迭代循环，一次获取每天每个省份的UV值
            for(String date : dateSet){
                //获取UV值
                Integer uv = dateMap.get(date);
                //set进行设置
                outputKey.set(date);
                outputValue.set(uv);

                //output，最终输出
                context.write(outputKey,outputValue);
            }
        }

    }

    /**
     * Execute the command with the given arguments.
     *
     * @param args
     *            command specific arguments.
     * @return exit code.
     * @throws Exception
     */
    // int run(String [] args) throws Exception;

    // step 3: Driver
    public int run(String[] args) throws Exception {

        Configuration configuration = this.getConf();

        Job job = Job.getInstance(configuration, this.getClass()
                .getSimpleName());
        job.setJarByClass(WebUVMapReduce.class);

        // set job
        // input
        Path inpath = new Path(args[0]);
        FileInputFormat.addInputPath(job, inpath);

        // output
        Path outPath = new Path(args[1]);
        FileOutputFormat.setOutputPath(job, outPath);

        // Mapper
        job.setMapperClass(WebUvMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        // ============shuffle=================
        // 1.partitioner
        // job.setPartitionerClass(cls);

        // 2.sort
        // job.setSortComparatorClass(cls);

        // 3.group
        // job.setGroupingComparatorClass(cls);

        // job.setCombinerClass(class);

        // ============shuffle=================

        // Reducer
        job.setReducerClass(WebUvReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // submit job -> YARN
        boolean isSuccess = job.waitForCompletion(true);
        return isSuccess ? 0 : 1;

    }

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();

        args = new String[] {
                "hdfs://MyDream:8020/home/hadoop/testdata/webuv/input",
                "hdfs://MyDream:8020/home/hadoop/testdata/webuv/output4" };

        // run job
        int status = ToolRunner.run(configuration, new WebUVMapReduce(), args);

        // exit program
        System.exit(status);
    }
}

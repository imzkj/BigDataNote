package sparkStreamingMLlib

import org.apache.spark.mllib.clustering.KMeansModel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.examples.streaming.StreamingExamples
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by 11725 on 2017/6/4.
  */
object SparknewsKeans {

  def main(args: Array[String]) {

    val conf=new SparkConf().setAppName("SparknewsKeans").setMaster("local")
    val sc=new SparkContext(conf)
    //设置滑动窗口，5秒钟读取一次数据
    val sstream=new StreamingContext()(conf,Seconds(5))
    //这些参数都是你在任务提交的时候自己定义的
    if (args.length < 4) {
      System.err.println("Usage: KafkaWordCount <zkQuorum> <group> <topics> <numThreads>")
      System.exit(1)
    }

    StreamingExamples.setStreamingLogLevels()

    val Array(zkQuorum, group, topics, numThreads) = args
    //设置检查点
    sc.checkpoint("checkpoint")
    val topicMap=topics.split(",").map((_,numThreads.toInt)).toMap
    //.map(_._2)从kafka读取数据
    val news=KafkaUtils.createStream(sstream,zkQuorum,group,topicMap).map(_._2)

    news.foreachRDD{ecahrdd=>
      //对每个rdd取第四个文章内容
      ecahrdd.map(line=>line.filter(x=>x.split(",")(4)))

    }

    val model=KMeansModel.load("xxxxx")
    //模型加载完之后，和前面一样，计算出词频，然后根据词频对我们的文章进行分类
  }

}

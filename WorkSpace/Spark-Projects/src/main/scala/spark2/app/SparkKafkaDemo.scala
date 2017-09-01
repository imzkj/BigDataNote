package spark2.app

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._

object SparkKafkaDemo {
//  def main(args: Array[String]) {
//
//    //第一种方式
//    //    val sparkConf = new SparkConf().setAppName("KafkaWordCount")
//    //    val ssc = new StreamingContext(sparkConf, Seconds(2))
//    //    ssc.checkpoint("checkpoint")
//
//    //    val topicMap = Map("zkjTopic" -> 1)
//    //
//    //    val lines = KafkaUtils.createStream(ssc, "MyDream:2181", "testKafka", topicMap).map(_._2)
//    //    val words = lines.flatMap(_.split(" "))
//    //    val wordCounts = words.map(x => (x, 1L))
//    //      .reduceByKeyAndWindow(_ + _, _ - _, Minutes(10), Seconds(2), 2)
//    //    wordCounts.print()
//
//    //第二种方式
//    // Create context with 2 second batch interval
//    val sparkConf = new SparkConf().setAppName("DirectKafkaWordCount")
//    val ssc = new StreamingContext(sparkConf, Seconds(2))
//
//    // Create direct kafka stream with brokers and topics
//    val topicsSet = Set("testTopic")
//    val kafkaParams = Map[String, String]("metadata.broker.list" -> "MyDream:9092")
//    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
//      ssc, kafkaParams, topicsSet)
//
//    // Get the lines, split them into words, count the words and print
//    val lines = messages.map(_._2)
//    val words = lines.flatMap(_.split(" "))
//
//    //统计历史单词个数
////    val updateFunc = (values: Seq[Int], state: Option[Int]) => {
////      val currentCount = values.sum
////      val previousCount = state.getOrElse(0)
////      Some(currentCount + previousCount)
////    }
////    val stateStream = words.map(x => (x, 1)) updateStateByKey[Int] (updateFunc)
////    stateStream.print()
//
//
//    val wordCounts = words.map(x => (x, 1L)).reduceByKey(_ + _)
//    wordCounts.print()
//
//    ssc.start()
//    ssc.awaitTermination()
//  }


}
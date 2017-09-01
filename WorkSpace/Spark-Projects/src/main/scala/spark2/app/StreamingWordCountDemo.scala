package spark2.app

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._ // not necessary since Spark 1.3

object StreamingWordCountDemo {
//  def main(args: Array[String]): Unit = {
//    //1.0版本
//    //    val conf = new SparkConf().setAppName("NetworkWordCount") //.setMaster("local")
//    //
//    //    val ssc = new StreamingContext(conf,Second(1))
//    //
//    //    val lines = ssc.socketTextStream("localhost",9999)
//    //
//    //    val words = lines.flatMap(_.split(" "))
//    //
//    //    val wordCounts = words.map((_,1)).reduceByKey(_ + _)
//    //
//    //    wordCounts.print()
//    //
//    //    ssc.start()
//    //
//    //    sc.awaitTermination()
//    //2.0版本
//    // Create a local StreamingContext with two working thread and batch interval of 1 second.
//    // The master requires 2 cores to prevent from a starvation scenario.
//
//    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
//    val ssc = new StreamingContext(conf, Seconds(1))
//    // Create a DStream that will connect to hostname:port, like localhost:9999
//    val lines = ssc.socketTextStream("localhost", 9999)
//    //监控hdfs文件
//    //val lines = ssc.textFileStream("hdfs://MyDream:9000/path")
//
//    // Split each line into words
//    val words = lines.flatMap(_.split(" "))
//    // Count each word in each batch
//    val pairs = words.map(word => (word, 1))
//    val wordCounts = pairs.reduceByKey(_ + _)
//
//    // Print the first ten elements of each RDD generated in this DStream to the console
//    wordCounts.print()
//    ssc.start()             // Start the computation
//    ssc.awaitTermination()  // Wait for the computation to terminate
//  }
}

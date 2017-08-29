package spark.app

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming._
import org.apache.spark.streaming.flume._

object SparkFlumeDemo {
  def main(args: Array[String]): Unit = {

    val batchInterval = Milliseconds(2000)

    // Create the context and set the batch size
    val sparkConf = new SparkConf().setAppName("FlumeEventCount")
    val ssc = new StreamingContext(sparkConf, batchInterval)

    // Create a flume stream
    //port为flume配置文件sink指定的端口
    val stream = FlumeUtils.createStream(ssc, "MyDream", 9999, StorageLevel.MEMORY_ONLY_SER_2)

    // Print out the count of events received from this server in each batch
    stream.count().map(cnt => "Received " + cnt + " flume events.").print()

    ssc.start()
    ssc.awaitTermination()
  }

}

package spark2.app

//import org.apache.spark.sql.SparkSession

object SparkDemo {
  def main(args: Array[String]): Unit = {
    //1.0版本
    //    val logFile = "hdfs://hadoop-senior.ibeifeng.com:8020/user/beifeng/mapreduce/wordcount/input/wc.input"
    //
    //    val conf = new SparkConf().setAppName("Simple Application") //.setMaster("local")
    //
    //    val sc = new SparkContext(conf)
    //
    //    val rdd = sc.textFile(logFile)
    //
    //    val wordcount = rdd.flatMap(_.split(" ")).map((_,1)).reduceByKey(_ + _)
    //
    //    wordcount.saveAsTextFile("hdfs://hadoop-senior.ibeifeng.com:8020/user/beifeng/mapreduce/wordcount/sparkOutput00")
    //
    //    sc.stop()
    //2.0版本
//    val logFile = "hdfs://mydream:9000/user/zkj/input/test.txt" // Should be some file on your system
//    val conf = SparkSession.builder.appName("Simple Application").master("local[*]").getOrCreate()
//    val rdd = conf.read.textFile(logFile).cache().rdd
//    val wordcount = rdd.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
//    wordcount.saveAsTextFile("hdfs://mydream:9000/user/zkj/output11")
//    conf.stop()
  }
}

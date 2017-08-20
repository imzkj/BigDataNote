package spark.app

import org.apache.spark.sql.SparkSession

object ScalaDemo {
  def main(args: Array[String]): Unit = {
    val logFile = "C:\\Users\\60186\\Downloads\\spark-2.2.0-bin-hadoop2.7\\spark-2.2.0-bin-hadoop2.7\\README.md" // Should be some file on your system
    val spark = SparkSession.builder.appName("Simple Application").master("local[*]").getOrCreate()
    val logData = spark.read.textFile(logFile).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")
    spark.stop()
  }
}

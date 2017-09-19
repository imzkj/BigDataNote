package spark1.sql

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

/**
 * @author Administrator
 */
object DataFrameCreateAndOperation {
  
  def main(args: Array[String]) {
    val conf = new SparkConf()
        .setAppName("DataFrameCreate")  
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
  
    val df = sqlContext.read.json("hdfs://MyDream:9000/students.json")

    df.show()
    df.printSchema()
    df.select("name").show()
    df.select(df("name"), df("age") + 1).show()
    df.filter(df("age") > 18).show()
    df.groupBy("age").count().show()
  }
  
}
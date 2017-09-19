package spark1.sql.loadSave

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

/**
 * @author Administrator
 */
object ManuallySpecifyOptions {
  
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
        .setAppName("ManuallySpecifyOptions")  
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
  
    val peopleDF = sqlContext.read.format("json").load("hdfs://spark1:9000/people.json")
    peopleDF.select("name").write.format("parquet").save("hdfs://spark1:9000/peopleName_scala")   
  }
  
}
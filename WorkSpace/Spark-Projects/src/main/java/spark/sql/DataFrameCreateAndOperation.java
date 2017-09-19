package spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

/**
 * 使用json文件创建DataFrame
 * @author Administrator
 *
 */

public class DataFrameCreateAndOperation {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf()
				.setAppName("DataFrameCreate");  
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);
		
		DataFrame df = sqlContext.read().json("hdfs://MyDream:9000/students.json");

		// 打印DataFrame中所有的数据（select * from ...）
		df.show();
		// 打印DataFrame的元数据（Schema）
		df.printSchema();
		// 查询某列所有的数据
		df.select("name").show();
		// 查询某几列所有的数据，并对列进行计算
		df.select(df.col("name"), df.col("age").plus(1)).show();
		// 根据某一列的值进行过滤
		df.filter(df.col("age").gt(18)).show();
		// 根据某一列进行分组，然后进行聚合
		df.groupBy(df.col("age")).count().show();
	}
	
}

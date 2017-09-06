package spark1.wordcount.core

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author Administrator
 */
object SortWordCount {
  
  def main(args: Array[String]) {
    val conf = new SparkConf()
        .setAppName("SortWordCount")
        .setMaster("local") 
    val sc = new SparkContext(conf)
    
    val lines = sc.textFile("C://Users//Administrator//Desktop//spark.txt", 1)
    val words = lines.flatMap { line => line.split(" ") }  
    val pairs = words.map { word => (word, 1) }  
    val wordCounts = pairs.reduceByKey(_ + _)  
    
    val countWords = wordCounts.map(wordCount => (wordCount._2, wordCount._1))   
    val sortedCountWords = countWords.sortByKey(false)  
    val sortedWordCounts = sortedCountWords.map(sortedCountWord => (sortedCountWord._2, sortedCountWord._1))  
    
    sortedWordCounts.foreach(sortedWordCount => println(
        sortedWordCount._1 + " appear " + sortedWordCount._2 + " times."))
  }
  
}
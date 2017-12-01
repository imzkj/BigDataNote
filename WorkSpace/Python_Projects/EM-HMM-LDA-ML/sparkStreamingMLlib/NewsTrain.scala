package sparkStreamingMLlib

import org.apache.spark.mllib.feature.{IDF, HashingTF}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.clustering.KMeans

/**
  * Created by 11725 on 2017/6/4.
  * 新闻的数据
  * title,time,author,context
  * *******************************文章都是要经过分词****************************
  */
object NewsTrain {

  def main(args: Array[String]) {

    val sc=new SparkContext()

    //我们对内容分类，是要取第三个参数context
    val newsrdd=sc.textFile("spark01:8080/log/news").map(_.split(" ")(3))


    val hashtf=new HashingTF()
    val tf=hashtf.transform(newsrdd)

    //第一遍计算IDF的向量，第二标通过IDF计算TF-IDF
    //minDocFreq这个参数的意义是过滤多少个单词
    val idf=new IDF(minDocFreq = 2).fit(tf)

    val tfidf=idf.transform(tf)

    //聚类导入模块
    val parsedata=tfidf
    val numclusters=14  //有6个聚类中
    val numIterors=20 //设置迭代次数
    //训练我们的模型
    val clusters=KMeans.train(parsedata,numclusters,numIterors)

    //打印出聚类中
    val clustercenters=clusters.clusterCenters

    //聚类结果标签,将原有的数据通过聚类找到中心点判断是哪些类别，并将文本标注出类别
    val labels=clusters.predict(parsedata)

    //保存模
    clusters.save(sc,"xxxxx")






  }

}

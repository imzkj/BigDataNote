package SparkSQLMLlib


import org.apache.spark.mllib.classification.{NaiveBayesModel, NaiveBayes}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.sql.SparkSession


/**
  * Created by 11725 on 2017/6/2.
  * 会话ID、用户ID、时间戳、页面url、访问时间、引用、时间跨度、打分
  * sessionid、userid、timestamp、pageurl、visittime、referrer、timespent、rating
  */
case class LogFlow(
                  sessid:String,
                  userid:String,
                  total:Int,
                  starttime:Long,
                  timespent:Long,
                  referffer:String,
                  exitpage:String,
                  flowstatus:Int
                  )
case class LogPage(
                    sessionid:String,
                    userid:String,
                    timestamp:String,
                    pageurl:String,
                    visittime:String,
                    referrer:String,
                    timespent:String,
                    rating:String
                  )
object SparkSQLETL {

  val ss=SparkSession.builder()
    .master("local")
    .appName("sqlTest")
    .getOrCreate()

  val sc=ss.sparkContext

  //读取文件   需要训练2017年5月份的数据
  val log=sc.textFile("http://168.192.148.3/log/2017-5")
  //对每一条数据进行过滤(map的作用)
  val loglist=log.map { log =>
    //每一条log如果长度不小于8，就是合法的
    if(log.split(" ").length>=8){
      val sessionid = log.split(" ")(0)
      val userid=log.split(" ")(1)
      val timestamp= log.split(" ")(2)
      val pageurl= log.split(" ")(3)
      val visittime= log.split(" ")(4)
      val referrer= log.split(" ")(5)
      val timespent= log.split(" ")(6)
      val rating= log.split(" ")(7)
      LogPage(sessionid,userid,timestamp,pageurl,visittime,referrer,timespent,rating)
    }
    //这里小于7的数据可能报空指针
//    else{
//      null
//    }
  }//.filter(x=>x==null)

  //把loglist的对象传给createDataFrame
  val schema=ss.createDataFrame(loglist)
  //创建一个中间表pages
  schema.createOrReplaceTempView("pages")

  //通过sql语句来判断找出评分大于1
  val sqlfiter=ss.sql("select * from pages where rating >1 ")

  val logrdd=sqlfiter.rdd.map{row=>
    val sessionid = row.getAs[String]("sessionid")
    val userid=row.getAs[String]("userid")
    val timestamp= row.getAs[String]("timestamp")
    val pageurl= row.getAs[String]("pageurl")
    val visittime= row.getAs[String]("visittime")
    val referrer= row.getAs[String]("referrer")
    val timespent= row.getAs[String]("timespent")
    val rating= row.getAs[String]("rating")
    //转化为了tuple8
    (sessionid,userid,timestamp,pageurl,visittime,referrer,timespent,rating)
  }

  //通过session进行分组,使每个访客独立
  val sessionrdd=logrdd.groupBy(x=>x._1)、

  //groupBy之后的数据的类型是sessionid   iterator(userid、timestamp、pageurl、visittime、referrer、timespent、rating)

  val timprdd=sessionrdd.map{valu=>
    //通过时间戳对日志进行排序
    val data=valu._2.toList.sortBy(_._2)

    //所有的pageurl给获取到
    val page=data.map(_._3)
    //每个session的页面统计
    val total=page.size
    data.head
    val endtime=data.last._4+data.last._2
    //每个session的时长统计

    val category=categorize(page)
  }

  def categorize(lis:List[String]):Int={
    //定位页面的分类方式
    //0用户之前没有访问任何之前定义的页面
    //1访客至少访问了一个页面
    //2访客按照之前定义的顺序访问了所有页面
    //不同的业务有不同的分标签的方式，有的是根据访问页面的长度，
    // 比如10-100--2   5-10--1    0-5--0
    0
  }

  //数据预处理
  val parsedata=sc.textFile("xxxx").map{line=>
    val parts=line.split(",")
    LabeledPoint(parts(0).toDouble,Vectors.dense(parts(1).split(" ").map(_.toDouble)))
  }

  //将数据集分为训练集和测试集
  val spilts=parsedata.randomSplit(Array(0.6,0.4),seed = 1L)
  val training=spilts(0)
  val test=spilts(1)

  //调用贝叶斯分类库
  val model=NaiveBayes.train(training,lambda = 1.0)
  val predictionAndLabel=test.map(p=>(model.predict(p.features),p.label))

  val accuracy=1.0*predictionAndLabel.filter(x=>x._1==x._2).count()/test.count()

  //保存模型
  model.save(sc,"xxxx")
  //加载模型
  val samemodel=NaiveBayesModel.load(sc,"xxxx")


  //实时预测分析
  //val fiterSessions=sessionrdd.filter(t=>
  //  model.predict(Utils.featurize(t))==2)
  //fiterSessions.print()



}


hadoop fs -rmr /output
hadoop jar mapr.jar org.robby.mr.flume.WordCount /flume/log /output


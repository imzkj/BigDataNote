hadoop fs -rm /input/*
hadoop fs -put data.txt /input/
hadoop fs -rmr /output
hadoop fs -mkdir /output
hadoop jar mapr.jar org.robby.mr.shortestpath.Main dee joe /input/data.txt /output


redis-cli flushall
hadoop fs -rm /input/*
hadoop fs -put data.txt /input/
hadoop fs -rmr /output/
hadoop jar mapr.jar org.robby.mr.shortestpath2.Main /input/data.txt /output/


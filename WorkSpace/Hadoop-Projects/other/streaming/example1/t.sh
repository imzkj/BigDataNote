
hadoop fs -rm /input/*
hadoop fs -put data1.txt /input
hadoop fs -put data2.txt /input
hadoop fs -rmr /output
hadoop jar /usr/share/hadoop/contrib/streaming/hadoop-streaming-1.0.4.jar -D mapred.reduce.tasks=2 -input /input -output /output -mapper /home/robby/proj/hadoop2/other/streaming/example1/mapper.sh -reducer /home/robby/proj/hadoop2/other/streaming/example1/reducer.sh
#hadoop jar /usr/share/hadoop/contrib/streaming/hadoop-streaming-1.0.4.jar -D mapred.reduce.tasks=2 -partitioner org.apache.hadoop.mapred.lib.TotalOrderPartitioner -input /input -output /output -mapper /home/robby/proj/hadoop2/other/streaming/example1/mapper.sh -reducer /home/robby/proj/hadoop2/other/streaming/example1/reducer.sh



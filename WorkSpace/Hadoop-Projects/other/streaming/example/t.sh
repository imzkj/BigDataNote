hadoop fs -rm /input/*
hadoop fs -put data*.txt /input/
hadoop fs -rmr /output
#hadoop jar /usr/share/hadoop/contrib/streaming/hadoop-streaming-1.0.4.jar -input /input -output /output -mapper /home/robby/proj/hadoop2/other/streaming/example/mapper.sh -reducer /home/robby/proj/hadoop2/other/streaming/example/reducer.sh
#hadoop jar /usr/share/hadoop/contrib/streaming/hadoop-streaming-1.0.4.jar -input /input -output /output -mapper /home/robby/proj/hadoop2/other/streaming/example/mapper.sh -reducer /home/robby/proj/hadoop2/other/streaming/example/reducer.sh -file /home/robby/proj/hadoop2/other/streaming/example/mapper.sh -file /home/robby/proj/hadoop2/other/streaming/example/reducer.sh -D mapred.map.tasks=1  
#hadoop jar /usr/share/hadoop/contrib/streaming/hadoop-streaming-1.0.4.jar -D mapred.reduce.tasks=2  -input /input -output /output -mapper /home/robby/proj/hadoop2/other/streaming/example/mapper.sh -reducer /home/robby/proj/hadoop2/other/streaming/example/reducer.sh -file /home/robby/proj/hadoop2/other/streaming/example/mapper.sh -file /home/robby/proj/hadoop2/other/streaming/example/reducer.sh 
hadoop jar /usr/share/hadoop/contrib/streaming/hadoop-streaming-1.0.4.jar -input /input -output /output -mapper /home/robby/proj/hadoop2/other/streaming/example/mapper.sh -combiner /home/robby/proj/hadoop2/other/streaming/example/reducer.sh -reducer /home/robby/proj/hadoop2/other/streaming/example/reducer.sh



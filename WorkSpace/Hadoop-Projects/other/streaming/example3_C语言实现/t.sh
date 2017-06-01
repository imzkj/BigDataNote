hadoop fs -rm /input/*
hadoop fs -put *.c /input/
hadoop fs -rmr /output

hadoop jar /usr/share/hadoop/contrib/streaming/hadoop-streaming-1.0.4.jar -input /input -output /output -mapper /home/robby/proj/hadoop2/other/streaming/example3/mapper -reducer /home/robby/proj/hadoop2/other/streaming/example3/reducer



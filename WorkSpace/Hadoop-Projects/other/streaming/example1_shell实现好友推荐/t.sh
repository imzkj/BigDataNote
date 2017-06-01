hadoop fs -rm /input/*

redis-cli keys ref_*>ref.txt
for i in `cat ref.txt`
do
        redis-cli del $i
done

redis-cli hkeys users>users.txt

hadoop fs -put users.txt /input

hadoop fs -rmr /output
#hadoop jar /usr/share/hadoop/contrib/streaming/hadoop-streaming-1.0.4.jar -D mapred.reduce.tasks=2 -input /input -output /output -mapper /home/robby/proj/hadoop2/other/streaming/example1/mapper.sh -reducer /home/robby/proj/hadoop2/other/streaming/example1/reducer.sh
hadoop jar /usr/share/hadoop/contrib/streaming/hadoop-streaming-1.0.4.jar -input /input -output /output -mapper /home/robby/proj/hadoop2/other/streaming/example2/mapper.sh



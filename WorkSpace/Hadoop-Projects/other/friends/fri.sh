redis-cli keys ref_*>ref.txt
for i in `cat ref.txt`
do
	redis-cli del $i
done

redis-cli hkeys users>users.txt


hadoop fs -put users.txt /input

hadoop fs -rmr /output
hadoop jar mapr.jar org.robby.mr.friend.Friends /input /output

hadoop fs -rm /input/*

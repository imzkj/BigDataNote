hadoop fs -rm /input/*
hadoop fs -put ./data.txt /input
hadoop fs -rmr /output
#hadoop pipes -D hadoop.pipes.java.recordreader=true -D hadoop.pipes.java.recordwriter=true -input /input -output /output -program /bin/pipetest
hadoop pipes -conf ./word.xml -input /input -output /output

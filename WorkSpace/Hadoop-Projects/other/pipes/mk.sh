g++ -m32 pipe.cc -I/home/robby/software/hadoop-1.0.4/src/c++/install/include   -L/home/robby/software/hadoop-1.0.4/src/c++/install/lib -lhadooppipes -lhadooputils  -lpthread -lcrypto -o pipetest
hadoop fs -rm /bin/pipetest
hadoop fs -put ./pipetest /bin

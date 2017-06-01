hadoop fs -rm /flume/log/*
flume-ng agent --conf-file app2.cf --name agent2 -Dfluem.root.logger=INFO,console

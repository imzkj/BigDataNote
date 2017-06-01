tmpfile=`date '+%Y%m%d%H%M%S'`.txt 
echo $tmpfile

for i in `find /home/robby/tmp/log -name localhost*`
do
  echo $i
  if [ -s $i ]
  then
     cat $i >> $tmpfile
     rm -f $i
  fi
done

if [ -s $tmpfile ]
then
  hadoop fs -put $tmpfile /input
  rm $tmpfile

  hadoop fs -rmr /output
  hadoop jar count.jar org.robby.mr.WordCount /input /output

  hadoop fs -rm /input/*
fi

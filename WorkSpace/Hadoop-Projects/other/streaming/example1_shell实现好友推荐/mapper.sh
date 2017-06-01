while read i
do
  echo $i
  redis-cli lrange fri_$i 0 -1
  
  for friend in `redis-cli lrange fri_$i 0 -1`
  do
          #echo $friend
          for friend2 in `redis-cli lrange fri_$friend 0 -1`
          do
          	  #echo $friend2
          	  found="false"
          	  for f in `redis-cli lrange fri_$i 0 -1`
          	  do
          	    if [ "$friend2" = "$f" ]
          	    then
          	        #echo "found"
          	        found="true"
          	    fi
          	  done
  
          	  if [ "$found" = "false" ]
          	  then
          	    if [ "$friend2" != "$i" ]
          	    then
          	      echo $friend2
          	      redis-cli zincrby ref_$i 1 $friend2
          	    fi
          	  fi
          done
  done
done
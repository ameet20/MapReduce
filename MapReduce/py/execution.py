$ $HADOOP_HOME/bin/hadoop jar contrib/streaming/hadoop-streaming-1.
2.1.jar \
   -input input_dirs \ 
   -output output_dir \ 
   -mapper <path/mapper.py \ 
   -reducer <path/reducer.py

GRID_DIR=`dirname "$0"`
GRID_DIR=`cd "$GRID_DIR"; pwd`
source $GRID_DIR/gridmix-env-2

# Smaller data set is used by default.
COMPRESSED_DATA_BYTES=2147483648
UNCOMPRESSED_DATA_BYTES=536870912

# Number of partitions for output data
NUM_MAPS=100

# If the env var USE_REAL_DATASET is set, then use the params to generate the bigger (real) dataset.
if [ ! -z ${USE_REAL_DATASET} ] ; then
  echo "Using real dataset"
  NUM_MAPS=492
  # 2TB data compressing to approx 500GB
  COMPRESSED_DATA_BYTES=2147483648000
  # 500GB
  UNCOMPRESSED_DATA_BYTES=536870912000
fi

## Data sources
export GRID_MIX_DATA=/gridmix/data
# Variable length key, value compressed SequenceFile
export VARCOMPSEQ=${GRID_MIX_DATA}/WebSimulationBlockCompressed
# Fixed length key, value compressed SequenceFile
export FIXCOMPSEQ=${GRID_MIX_DATA}/MonsterQueryBlockCompressed
# Variable length key, value uncompressed Text File
export VARINFLTEXT=${GRID_MIX_DATA}/SortUncompressed
# Fixed length key, value compressed Text File
export FIXCOMPTEXT=${GRID_MIX_DATA}/EntropySimulationCompressed

${HADOOP_PREFIX}/bin/hadoop jar \
  ${EXAMPLE_JAR} randomtextwriter \
  -D mapreduce.randomtextwriter.totalbytes=${COMPRESSED_DATA_BYTES} \
  -D mapreduce.randomtextwriter.bytespermap=$((${COMPRESSED_DATA_BYTES} / ${NUM_MAPS})) \
  -D mapreduce.randomtextwriter.minwordskey=5 \
  -D mapreduce.randomtextwriter.maxwordskey=10 \
  -D mapreduce.randomtextwriter.minwordsvalue=100 \
  -D mapreduce.randomtextwriter.maxwordsvalue=10000 \
  -D mapreduce.output.fileoutputformat.compress=true \
  -D mapred.map.output.compression.type=BLOCK \
  -outFormat org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat \
  ${VARCOMPSEQ} &


${HADOOP_PREFIX}/bin/hadoop jar \
  ${EXAMPLE_JAR} randomtextwriter \
  -D mapreduce.randomtextwriter.totalbytes=${COMPRESSED_DATA_BYTES} \
  -D mapreduce.randomtextwriter.bytespermap=$((${COMPRESSED_DATA_BYTES} / ${NUM_MAPS})) \
  -D mapreduce.randomtextwriter.minwordskey=5 \
  -D mapreduce.randomtextwriter.maxwordskey=5 \
  -D mapreduce.randomtextwriter.minwordsvalue=100 \
  -D mapreduce.randomtextwriter.maxwordsvalue=100 \
  -D mapreduce.output.fileoutputformat.compress=true \
  -D mapred.map.output.compression.type=BLOCK \
  -outFormat org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat \
  ${FIXCOMPSEQ} &


${HADOOP_PREFIX}/bin/hadoop jar \
  ${EXAMPLE_JAR} randomtextwriter \
  -D mapreduce.randomtextwriter.totalbytes=${UNCOMPRESSED_DATA_BYTES} \
  -D mapreduce.randomtextwriter.bytespermap=$((${UNCOMPRESSED_DATA_BYTES} / ${NUM_MAPS})) \
  -D mapreduce.randomtextwriter.minwordskey=1 \
  -D mapreduce.randomtextwriter.maxwordskey=10 \
  -D mapreduce.randomtextwriter.minwordsvalue=0 \
  -D mapreduce.randomtextwriter.maxwordsvalue=200 \
  -D mapreduce.output.fileoutputformat.compress=false \
  -outFormat org.apache.hadoop.mapreduce.lib.output.TextOutputFormat \
  ${VARINFLTEXT} &


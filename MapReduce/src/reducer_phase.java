public static class Reduce extends Reducer<Text,IntWritable,Text,IntWritable> {
 
public void reduce(Text key, Iterable<IntWritable> values,Context context)
throws IOException,InterruptedException {
 
int sum=0;
for(IntWritable x: values)
{
sum+=x.get();
}
context.write(key, new IntWritable(sum));
}
}

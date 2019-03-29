public static class Map extends Mapper<LongWritable,Text,Text,IntWritable> {
 
public void map(LongWritable key, Text value, Context context) throws IOException,InterruptedException {
 
String line = value.toString();
StringTokenizer tokenizer = new StringTokenizer(line);
while (tokenizer.hasMoreTokens()) {
value.set(tokenizer.nextToken());
context.write(value, new IntWritable(1));
}

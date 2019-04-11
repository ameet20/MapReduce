package utils.csv;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVMultiLineInputFormat extends FileInputFormat<LongWritable, List<Text>> {

    public static final String LINES_PER_MAP = "mapreduce.input.lineinputformat.linespermap";

    public static final int DEFAULT_LINES_PER_MAP = 1;
    
    @Override
    public RecordReader<LongWritable, List<Text>> createRecordReader(InputSplit split, TaskAttemptContext context)
            throws IOException {
        Configuration conf = context.getConfiguration();
        String quote = conf.get(CSVLineRecordReader.FORMAT_DELIMITER, CSVLineRecordReader.DEFAULT_DELIMITER);
        String separator = conf.get(CSVLineRecordReader.FORMAT_SEPARATOR, CSVLineRecordReader.DEFAULT_SEPARATOR);
        if (null == quote || null == separator) {
            throw new IOException("CSVTextInputFormat: missing parameter delimiter/separator");
        }
        context.setStatus(split.toString());
        return new CSVLineRecordReader();
    }
public List<InputSplit> getSplits(JobContext job) throws IOException {
        List<InputSplit> splits = new ArrayList<InputSplit>();
        int numLinesPerSplit = getNumLinesPerSplit(job);
        for (FileStatus status : listStatus(job)) {
            List<FileSplit> fileSplits = getSplitsForFile(status, job.getConfiguration(), numLinesPerSplit);
            splits.addAll(fileSplits);
        }
        return splits;
    }
    
    public static List<FileSplit> getSplitsForFile(FileStatus status, Configuration conf, int numLinesPerSplit)
            throws IOException {
        List<FileSplit> splits = new ArrayList<FileSplit>();
        Path fileName = status.getPath();
        if (status.isDir()) {
            throw new IOException("Not a file: " + fileName);
        }
        FileSystem fs = fileName.getFileSystem(conf);
        CSVLineRecordReader lr = null;
        try {
            FSDataInputStream in = fs.open(fileName);
            lr = new CSVLineRecordReader(in, conf);
            List<Text> line = new ArrayList<Text>();
            int numLines = 0;
            long begin = 0;
            long length = 0;
            int num = -1;
            while ((num = lr.readLine(line)) > 0) {
                numLines++;
                length += num;
                if (numLines == numLinesPerSplit) {
                    // To make sure that each mapper gets N lines,
                    // we move back the upper split limits of each split
                    // by one character here.
                    if (begin == 0) {
                        splits.add(new FileSplit(fileName, begin, length - 1, new String[] {}));
                    } else {
                        splits.add(new FileSplit(fileName, begin, length - 1, new String[] {}));
                    }
                    begin += length;
                    length = 0;
                    numLines = 0;
                }
            }
            if (numLines != 0) {
                splits.add(new FileSplit(fileName, begin, length, new String[] {}));
            }
        } finally {
            if (lr != null) {
                lr.close();
            }
        }
        return splits;
    }
     @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return true;
    }
    public static void setNumLinesPerSplit(Job job, int numLines) {
        job.getConfiguration().setInt(LINES_PER_MAP, numLines);
    }
    public static int getNumLinesPerSplit(JobContext job) {
        return job.getConfiguration().getInt(LINES_PER_MAP, DEFAULT_LINES_PER_MAP);
    }

}

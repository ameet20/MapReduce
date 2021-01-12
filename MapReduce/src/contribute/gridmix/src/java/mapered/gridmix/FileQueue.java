package org.apache.hadoop.mapred.gridmix;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;

/**
 * Given a {@link org.apache.hadoop.mapreduce.lib.input.CombineFileSplit},
 * circularly read through each input source.
 */
class FileQueue extends InputStream {

  private int idx = -1;
  private long curlen = -1L;
  private InputStream input;
  private final byte[] z = new byte[1];
  private final Path[] paths;
  private final long[] lengths;
  private final long[] startoffset;
  private final Configuration conf;

  /**
   * @param split Description of input sources.
   * @param conf Used to resolve FileSystem instances.
   */
  public FileQueue(CombineFileSplit split, Configuration conf)
      throws IOException {
    this.conf = conf;
    paths = split.getPaths();
    startoffset = split.getStartOffsets();
    lengths = split.getLengths();
    nextSource();
  }

  protected void nextSource() throws IOException {
    if (0 == paths.length) {
      return;
    }
    if (input != null) {
      input.close();
    }
    idx = (idx + 1) % paths.length;
    curlen = lengths[idx];
    final Path file = paths[idx];
    input = 
      CompressionEmulationUtil.getPossiblyDecompressedInputStream(file, 
                                 conf, startoffset[idx]);
  }

  @Override
  public int read() throws IOException {
    final int tmp = read(z);
    return tmp == -1 ? -1 : (0xFF & z[0]);
  }

  @Override
  public int read(byte[] b) throws IOException {
    return read(b, 0, b.length);
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    int kvread = 0;
    while (kvread < len) {
      if (curlen <= 0) {
        nextSource();
        continue;
      }
      final int srcRead = (int) Math.min(len - kvread, curlen);
      IOUtils.readFully(input, b, kvread, srcRead);
      curlen -= srcRead;
      kvread += srcRead;
    }
    return kvread;
  }

  @Override
  public void close() throws IOException {
    input.close();
  }

}

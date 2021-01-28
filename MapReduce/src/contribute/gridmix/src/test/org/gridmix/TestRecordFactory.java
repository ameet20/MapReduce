package org.apache.hadoop.mapred.gridmix;

import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DataOutputBuffer;

public class TestRecordFactory {
  private static final Log LOG = LogFactory.getLog(TestRecordFactory.class);

  public static void testFactory(long targetBytes, long targetRecs)
      throws Exception {
    final Configuration conf = new Configuration();
    final GridmixKey key = new GridmixKey();
    final GridmixRecord val = new GridmixRecord();
    LOG.info("Target bytes/records: " + targetBytes + "/" + targetRecs);
    final RecordFactory f = new AvgRecordFactory(targetBytes, targetRecs, conf);
    targetRecs = targetRecs <= 0 && targetBytes >= 0
      ? Math.max(1,
                 targetBytes 
                 / conf.getInt(AvgRecordFactory.GRIDMIX_MISSING_REC_SIZE, 
                               64 * 1024))
      : targetRecs;

    long records = 0L;
    final DataOutputBuffer out = new DataOutputBuffer();
    while (f.next(key, val)) {
      ++records;
      key.write(out);
      val.write(out);
    }
    assertEquals(targetRecs, records);
    assertEquals(targetBytes, out.getLength());
  }

  @Test
  public void testRandom() throws Exception {
    final Random r = new Random();
    final long targetBytes = r.nextInt(1 << 20) + 3 * (1 << 14);
    final long targetRecs = r.nextInt(1 << 14);
    testFactory(targetBytes, targetRecs);
  }

  @Test
  public void testAvg() throws Exception {
    final Random r = new Random();
    final long avgsize = r.nextInt(1 << 10) + 1;
    final long targetRecs = r.nextInt(1 << 14);
    testFactory(targetRecs * avgsize, targetRecs);
  }

  @Test
  public void testZero() throws Exception {
    final Random r = new Random();
    final long targetBytes = r.nextInt(1 << 20);
    testFactory(targetBytes, 0);
  }
}

package org.apache.hadoop.mapred.lib;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobConfigurable;
import org.apache.hadoop.mapreduce.JobContext;

/**
 * This comparator implementation provides a subset of the features provided
 * by the Unix/GNU Sort. In particular, the supported features are:
 * -n, (Sort numerically)
 * -r, (Reverse the result of comparison)
 * -k pos1[,pos2], where pos is of the form f[.c][opts], where f is the number
 *  of the field to use, and c is the number of the first character from the
 *  beginning of the field. Fields and character posns are numbered starting
 *  with 1; a character position of zero in pos2 indicates the field's last
 *  character. If '.c' is omitted from pos1, it defaults to 1 (the beginning
 *  of the field); if omitted from pos2, it defaults to 0 (the end of the
 *  field). opts are ordering options (any of 'nr' as described above). 
 * We assume that the fields in the key are separated by
 * {@link JobContext#MAP_OUTPUT_KEY_FIELD_SEPERATOR} 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.partition.KeyFieldBasedComparator} 
 * instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class KeyFieldBasedComparator<K, V> extends 
    org.apache.hadoop.mapreduce.lib.partition.KeyFieldBasedComparator<K, V>
    implements JobConfigurable {

  public void configure(JobConf job) {
    super.setConf(job);
  }
}

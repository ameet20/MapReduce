package org.apache.hadoop.mapreduce.jobhistory;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * Interface for event wrapper classes.  Implementations each wrap an
 * Avro-generated class, adding constructors and accessor methods.
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public interface HistoryEvent {

  /** Return this event's type. */
  EventType getEventType();

  /** Return the Avro datum wrapped by this. */
  Object getDatum();

  /** Set the Avro datum wrapped by this. */
  void setDatum(Object datum);
}

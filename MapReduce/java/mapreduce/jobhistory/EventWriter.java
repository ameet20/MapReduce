package org.apache.hadoop.mapreduce.jobhistory;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.CounterGroup;
import org.apache.hadoop.mapreduce.Counters;

import org.apache.avro.Schema;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.JsonEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.util.Utf8;

/**
 * Event Writer is an utility class used to write events to the underlying
 * stream. Typically, one event writer (which translates to one stream) 
 * is created per job 
 * 
 */
class EventWriter {
  static final String VERSION = "Avro-Json";

  private FSDataOutputStream out;
  private DatumWriter<Event> writer =
    new SpecificDatumWriter<Event>(Event.class);
  private Encoder encoder;
  
  EventWriter(FSDataOutputStream out) throws IOException {
    this.out = out;
    out.writeBytes(VERSION);
    out.writeBytes("\n");
    out.writeBytes(Event.SCHEMA$.toString());
    out.writeBytes("\n");
    this.encoder = new JsonEncoder(Event.SCHEMA$, out);
  }
  
  synchronized void write(HistoryEvent event) throws IOException { 
    Event wrapper = new Event();
    wrapper.type = event.getEventType();
    wrapper.event = event.getDatum();
    writer.write(wrapper, encoder);
    encoder.flush();
    out.writeBytes("\n");
  }
  
  void flush() throws IOException { 
    encoder.flush();
  }

  void close() throws IOException {
    encoder.flush();
    out.close();
  }

  private static final Schema GROUPS =
    Schema.createArray(JhCounterGroup.SCHEMA$);

  private static final Schema COUNTERS =
    Schema.createArray(JhCounter.SCHEMA$);

  static JhCounters toAvro(Counters counters) {
    return toAvro(counters, "COUNTERS");
  }
  static JhCounters toAvro(Counters counters, String name) {
    JhCounters result = new JhCounters();
    result.name = new Utf8(name);
    result.groups = new GenericData.Array<JhCounterGroup>(0, GROUPS);
    if (counters == null) return result;
    for (CounterGroup group : counters) {
      JhCounterGroup g = new JhCounterGroup();
      g.name = new Utf8(group.getName());
      g.displayName = new Utf8(group.getDisplayName());
      g.counts = new GenericData.Array<JhCounter>(group.size(), COUNTERS);
      for (Counter counter : group) {
        JhCounter c = new JhCounter();
        c.name = new Utf8(counter.getName());
        c.displayName = new Utf8(counter.getDisplayName());
        c.value = counter.getValue();
        g.counts.add(c);
      }
      result.groups.add(g);
    }
    return result;
  }

}

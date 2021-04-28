package org.apache.hadoop.mapred;

import java.io.IOException;
import java.util.List;

/**
 * Interface for entities that handle events.
 */
public interface SimulatorEventListener {
  /**
   * Get the initial events to put in event queue.
   * @param when time to schedule the initial events
   * @return list of the initial events
   */
  List<SimulatorEvent> init(long when) throws IOException;
  
  /**
   * Process an event, generate more events to put in event queue.
   * @param event the event to be processed
   * @return list of generated events by processing this event
   */
  List<SimulatorEvent> accept(SimulatorEvent event) throws IOException;
}

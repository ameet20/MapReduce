package org.apache.hadoop.mapred;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

// HashSet and HashMap do not guarantee that the oder of iteration is 
// determinstic. We need the latter for the deterministic replay of 
// simulations. These iterations are heavily used in the JobTracker, e.g. when 
// looking for non-local map tasks. Not all HashSet and HashMap instances
// are iterated over, but to be safe and simple we replace all with 
// LinkedHashSet and LinkedHashMap whose iteration order is deterministic.

public privileged aspect DeterministicCollectionAspects {

  // Fortunately the Java runtime type of generic classes do not contain
  // the generic parameter. We can catch all with a single base pattern using
  // the erasure of the generic type.

  HashSet around() : call(HashSet.new()) {
    return new LinkedHashSet();
  }

  HashMap around() : call(HashMap.new()) {
    return new LinkedHashMap();
  }

  ConcurrentHashMap around() : call(ConcurrentHashMap.new()) {
    return new FakeConcurrentHashMap();
  }
}

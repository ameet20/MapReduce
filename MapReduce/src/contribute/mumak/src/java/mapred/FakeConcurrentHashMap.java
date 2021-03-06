package org.apache.hadoop.mapred;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A fake ConcurrentHashMap implementation that maintains the insertion order of
 * entries when traversing through iterator. The class is to support
 * deterministic replay of Mumak and not meant to be used as a ConcurrentHashMap
 * replacement with multiple threads.
 */
public class FakeConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {
  private final Map<K, V> map;
  
  public FakeConcurrentHashMap() {
    map = new LinkedHashMap<K, V>();
  }
  
  @Override
  public V putIfAbsent(K key, V value) {
    if (!containsKey(key)) {
      return put(key, value);
    } else {
      return get(key);
    }
  }

  @Override
  public boolean remove(Object key, Object value) {
    if (!containsKey(key)) return false;
    Object oldValue = get(key);
    if ((oldValue == null) ? value == null : oldValue.equals(value)) {
      remove(key);
      return true;
    }
    return false;
  }

  @Override
  public V replace(K key, V value) {
    if (containsKey(key)) {
      return put(key, value);
    } else {
      return null;
    }
  }

  @Override
  public boolean replace(K key, V oldValue, V newValue) {
    if (!containsKey(key)) return false;
    Object origValue = get(key);
    if ((origValue == null) ? oldValue == null : origValue.equals(oldValue)) {
      put(key, newValue);
      return true;
    }
    return false;
  }

  @Override
  public void clear() {
    map.clear();
  }

  @Override
  public boolean containsKey(Object key) {
    return map.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return map.containsValue(value);
  }

  @Override
  public Set<Map.Entry<K, V>> entrySet() {
    return map.entrySet();
  }

  @Override
  public V get(Object key) {
    return map.get(key);
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public Set<K> keySet() {
    return map.keySet();
  }

  @Override
  public V put(K key, V value) {
    return map.put(key, value);
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> t) {
    map.putAll(t);
  }

  @Override
  public V remove(Object key) {
    return map.remove(key);
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public Collection<V> values() {
    return map.values();
  }
}

package com.andy.Util;

/**
 * Created by andy on 29/10/15.
 */
public class Pair<K, V> {

    private K key;
    private V value;

    public Pair(K k, V v) {
        key = k;
        value = v;
    }

    public K key() {
        return key;
    }

    public V value() {
        return value;
    }

    public void key(K k) {
        key = k;
    }

    public void value(V v) {
        value = v;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}

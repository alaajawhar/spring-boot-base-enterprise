package com.amdose.base.devportal.cachdata;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Alaa Jawhar
 */
public class CircularLinkedMap<K, V> extends LinkedHashMap<K, V> {

    private static int MAX_ENTRIES;

    public CircularLinkedMap(int maxEntries) {
        super(16, .75f, true);
        MAX_ENTRIES = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_ENTRIES;
    }

}
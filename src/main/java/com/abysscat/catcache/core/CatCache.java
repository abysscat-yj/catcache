package com.abysscat.catcache.core;

import com.abysscat.catcache.model.CacheEntry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * cat cache.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/16 1:24
 */
public class CatCache {

	private final static Map<String, CacheEntry<?>> CACHE_MAP = new HashMap<>();

	public String get(String key) {
		CacheEntry<String> entry = (CacheEntry<String>) CACHE_MAP.get(key);
		if (entry == null) return null;
		return entry.getValue();
	}

	public void set(String key, String value) {
		CACHE_MAP.put(key, new CacheEntry<>(value));
	}

	public int del(String... keys) {
		return keys == null ? 0 : (int) Arrays.stream(keys)
				.map(CACHE_MAP::remove).filter(Objects::nonNull).count();
	}

	public int exists(String... keys) {
		return keys == null ? 0 : (int) Arrays.stream(keys)
				.map(CACHE_MAP::containsKey).filter(x -> x).count();
	}

	public String[] mget(String... keys) {
		return keys == null ? new String[0] : Arrays.stream(keys)
				.map(this::get).toArray(String[]::new);
	}


	public void mset(String[] keys, String[] vals) {
		if (keys == null || keys.length == 0) return;
		for (int i = 0; i < keys.length; i++) {
			set(keys[i], vals[i]);
		}
	}

	public int incr(String key) {
		String str = get(key);
		int val = 0;
		try {
			if (str != null) {
				val = Integer.parseInt(str);
			}
			val++;
			set(key, String.valueOf(val));
		} catch (NumberFormatException nfe) {
			throw nfe;
		}
		return val;
	}

	public int decr(String key) {
		String str = get(key);
		int val = 0;
		try {
			if (str != null) {
				val = Integer.parseInt(str);
			}
			val--;
			set(key, String.valueOf(val));
		} catch (NumberFormatException nfe) {
			throw nfe;
		}
		return val;
	}

	public Integer strlen(String key) {
		return get(key) == null ? null : get(key).length();
	}

	// ===============  1. String end ===========

	// ===============  2. list  ===========
	public Integer lpush(String key, String... vals) {
		CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>) CACHE_MAP.get(key);
		if (entry == null) {
			entry = new CacheEntry<>(new LinkedList<>());
			CACHE_MAP.put(key, entry);
		}
		LinkedList<String> list = entry.getValue();
		Arrays.stream(vals).forEach(list::addFirst);
		return vals.length;
	}

	public String[] lpop(String key, int count) {
		CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>) CACHE_MAP.get(key);
		if (entry == null) return null;
		LinkedList<String> list = entry.getValue();
		if (list == null) return null;
		int len = Math.min(count, list.size());
		String[] ret = new String[len];
		int index = 0;
		while (index < len) {
			ret[index++] = list.removeFirst();
		}
		return ret;
	}

	public Integer rpush(String key, String... vals) {
		CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>) CACHE_MAP.get(key);
		if (entry == null) {
			entry = new CacheEntry<>(new LinkedList<>());
			this.CACHE_MAP.put(key, entry);
		}
		LinkedList<String> list = entry.getValue();
//        Arrays.stream(vals).forEach(list::addLast);
		list.addAll(List.of(vals));
		return vals.length;
	}

	public String[] rpop(String key, int count) {
		CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>) CACHE_MAP.get(key);
		if (entry == null) return null;
		LinkedList<String> list = entry.getValue();
		if (list == null) return null;
		int len = Math.min(count, list.size());
		String[] ret = new String[len];
		int index = 0;
		while (index < len) {
			ret[index++] = list.removeLast();
		}
		return ret;
	}

	public int llen(String key) {
		CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>) CACHE_MAP.get(key);
		if (entry == null) return 0;
		LinkedList<String> list = entry.getValue();
		if (list == null) return 0;
		return list.size();
	}

	public String lindex(String key, int index) {
		CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>) CACHE_MAP.get(key);
		if (entry == null) return null;
		LinkedList<String> list = entry.getValue();
		if (list == null) return null;
		if (index >= list.size()) return null;
		return list.get(index);
	}

	public String[] lrange(String key, int start, int end) {
		CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>) CACHE_MAP.get(key);
		if (entry == null) return null;
		LinkedList<String> list = entry.getValue();
		if (list == null) return null;
		int size = list.size();
		if (start >= size) return null;
		if (end >= size) end = size - 1;
		int len = Math.min(size, end - start + 1);
		String[] ret = new String[len];
		for (int i = 0; i < len; i++) {
			ret[i] = list.get(start + i);
		}
		return ret;
	}


	// ===============  2. list end ===========


}

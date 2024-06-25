package com.abysscat.catcache.core;

import com.abysscat.catcache.model.CacheEntry;
import com.abysscat.catcache.model.ZsetEntry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

/**
 * cat cache.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/16 1:24
 */
public class CatCache {

	private final static Map<String, CacheEntry<?>> CACHE_MAP = new HashMap<>();

	Random random = new Random();

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

	// ===============  3. set  ===========

	public Integer sadd(String key, String[] vals) {
		CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) CACHE_MAP.get(key);
		if (entry == null) {
			entry = new CacheEntry<>(new LinkedHashSet<>());
			CACHE_MAP.put(key, entry);
		}
		LinkedHashSet<String> set = entry.getValue();
		set.addAll(Arrays.asList(vals));
		return vals.length;
	}

	public String[] smembers(String key) {
		CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) CACHE_MAP.get(key);
		if (entry == null) return null;
		LinkedHashSet<String> set = entry.getValue();
		return set.toArray(String[]::new);
	}

	public Integer scard(String key) {
		CacheEntry<?> entry = CACHE_MAP.get(key);
		if (entry == null) return null;
		LinkedHashSet<?> set = (LinkedHashSet<?>) entry.getValue();
		return set.size();
	}

	public Integer sismember(String key, String val) {
		CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) CACHE_MAP.get(key);
		if (entry == null) return 0;
		LinkedHashSet<String> set = entry.getValue();
		return set.contains(val) ? 1 : 0;
	}

	public Integer srem(String key, String[] vals) {
		CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) CACHE_MAP.get(key);
		if (entry == null) return 0;
		LinkedHashSet<String> set = entry.getValue();
		return vals == null ? 0 : (int) Arrays.stream(vals)
				.map(set::remove).filter(x -> x).count();
	}

	public String[] spop(String key, int count) {
		CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) CACHE_MAP.get(key);
		if (entry == null) return null;
		LinkedHashSet<String> set = entry.getValue();
		if (set == null) return null;
		int len = Math.min(count, set.size());
		String[] ret = new String[len];
		int index = 0;
		while (index < len) {
			String[] array = set.toArray(String[]::new);
			String obj = array[random.nextInt(set.size())];
			set.remove(obj);
			ret[index++] = obj;
		}
		return ret;
	}

	// ===============  3. set end ===========


	// ===============  4. hash ===========

	public Integer hset(String key, String[] hkeys, String[] hvals) {
		if (hkeys == null || hkeys.length == 0) return 0;
		if (hvals == null || hvals.length == 0) return 0;
		CacheEntry<LinkedHashMap<String, String>> entry = (CacheEntry<LinkedHashMap<String, String>>) CACHE_MAP.get(key);
		if (entry == null) {
			entry = new CacheEntry<>(new LinkedHashMap<>());
			this.CACHE_MAP.put(key, entry);
		}
		LinkedHashMap<String, String> map = entry.getValue();
		for (int i = 0; i < hkeys.length; i++) {
			map.put(hkeys[i], hvals[i]);
		}
		return (int) Arrays.stream(hkeys).distinct().count();
	}

	public String hget(String key, String hkey) {
		CacheEntry<LinkedHashMap<String, String>> entry = (CacheEntry<LinkedHashMap<String, String>>) CACHE_MAP.get(key);
		if (entry == null) return null;
		LinkedHashMap<String, String> map = entry.getValue();
		return map.get(hkey);
	}

	public String[] hgetall(String key) {
		CacheEntry<LinkedHashMap<String, String>> entry = (CacheEntry<LinkedHashMap<String, String>>) CACHE_MAP.get(key);
		if (entry == null) return null;
		LinkedHashMap<String, String> map = entry.getValue();
		return map.entrySet().stream()
				.flatMap(e -> Stream.of(e.getKey(), e.getValue())).toArray(String[]::new);
	}

	public String[] hmget(String key, String[] hkeys) {
		CacheEntry<LinkedHashMap<String, String>> entry = (CacheEntry<LinkedHashMap<String, String>>) CACHE_MAP.get(key);
		if (entry == null) return null;
		LinkedHashMap<String, String> map = entry.getValue();

		return hkeys == null ? new String[0] : Arrays.stream(hkeys)
				.map(map::get).toArray(String[]::new);
	}

	public Integer hlen(String key) {
		CacheEntry<LinkedHashMap<String, String>> entry = (CacheEntry<LinkedHashMap<String, String>>) CACHE_MAP.get(key);
		if (entry == null) return 0;
		LinkedHashMap<String, String> map = entry.getValue();
		return map.size();
	}

	public Integer hexists(String key, String hkey) {
		CacheEntry<LinkedHashMap<String, String>> entry = (CacheEntry<LinkedHashMap<String, String>>) CACHE_MAP.get(key);
		if (entry == null) return 0;
		LinkedHashMap<String, String> map = entry.getValue();
		return map.containsKey(hkey) ? 1 : 0;
	}

	public Integer hdel(String key, String[] hkeys) {
		CacheEntry<LinkedHashMap<String, String>> entry = (CacheEntry<LinkedHashMap<String, String>>) CACHE_MAP.get(key);
		if (entry == null) return 0;
		LinkedHashMap<String, String> map = entry.getValue();
		return hkeys == null ? 0 : (int) Arrays.stream(hkeys)
				.map(map::remove).filter(Objects::nonNull).count();
	}

	// ===============  4. hash end ===========


	// ===============  5. zset  ===========
	public Integer zadd(String key, String[] values, double[] scores) {
		CacheEntry<LinkedHashSet<ZsetEntry>> entry = (CacheEntry<LinkedHashSet<ZsetEntry>>) CACHE_MAP.get(key);
		if (entry == null) {
			entry = new CacheEntry<>(new LinkedHashSet<>());
			this.CACHE_MAP.put(key, entry);
		}
		LinkedHashSet<ZsetEntry> set = entry.getValue();
		for (int i = 0; i < values.length; i++) {
			set.add(new ZsetEntry(values[i], scores[i]));
		}
		return values.length;
	}

	public Integer zcard(String key) {
		CacheEntry<?> entry = CACHE_MAP.get(key);
		if (entry == null) return 0;
		LinkedHashSet<?> set = (LinkedHashSet<?>) entry.getValue();
		return set.size();
	}

	public Integer zcount(String key, double min, double max) {
		CacheEntry<?> entry = CACHE_MAP.get(key);
		if (entry == null) return 0;
		LinkedHashSet<ZsetEntry> set = (LinkedHashSet<ZsetEntry>) entry.getValue();
		return (int) set.stream().filter(x -> x.getScore() >= min && x.getScore() <= max).count();
	}

	public Double zscore(String key, String val) {
		CacheEntry<?> entry = CACHE_MAP.get(key);
		if (entry == null) return null;
		LinkedHashSet<ZsetEntry> set = (LinkedHashSet<ZsetEntry>) entry.getValue();
		return set.stream().filter(x -> x.getValue().equals(val))
				.map(ZsetEntry::getScore).findFirst().orElse(null);
	}

	public Integer zrank(String key, String val) {
		CacheEntry<?> entry = CACHE_MAP.get(key);
		if (entry == null) return null;
		LinkedHashSet<ZsetEntry> set = (LinkedHashSet<ZsetEntry>) entry.getValue();
		Double zscore = zscore(key, val);
		if (zscore == null) return null;
		return (int) set.stream().filter(x -> x.getScore() < zscore).count();
	}

	public Integer zrem(String key, String[] vals) {
		CacheEntry<?> entry = CACHE_MAP.get(key);
		if (entry == null) return null;
		LinkedHashSet<ZsetEntry> set = (LinkedHashSet<ZsetEntry>) entry.getValue();
		return vals == null ? 0 : (int) Arrays.stream(vals)
				.map(x -> set.removeIf(y -> y.getValue().equals(x)))
				.filter(x -> x).count();
	}

	// ===============  5. zset end ===========

}

package com.abysscat.catcache.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * cat cache.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/16 1:24
 */
public class CatCache {

	private final static Map<String, String> CACHE_MAP = new HashMap<>();

	public String get(String key) {
		return CACHE_MAP.get(key);
	}

	public void set(String key, String value) {
		CACHE_MAP.put(key, value);
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
				.map(CACHE_MAP::get).toArray(String[]::new);
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

}

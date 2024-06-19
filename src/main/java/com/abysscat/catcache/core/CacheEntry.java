package com.abysscat.catcache.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * cache entry.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 1:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CacheEntry<T> {

	private T value;

}

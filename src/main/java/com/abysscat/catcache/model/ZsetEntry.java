package com.abysscat.catcache.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Zset entry model.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/23 0:48
 */
@Data
@AllArgsConstructor
public class ZsetEntry {

	private String value;

	private double score;

}

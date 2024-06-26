package com.abysscat.catcache.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * command reply.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply<T> {

	T value;
	ReplyType type;

	public static Reply<String> string(String value) {
		return new Reply<>(value, ReplyType.SIMPLE_STRING);
	}

	public static Reply<String> bulkString(String value) {
		return new Reply<>(value, ReplyType.BULK_STRING);
	}

	public static Reply<Integer> integer(Integer value) {
		return new Reply<>(value, ReplyType.INT);
	}

	public static Reply<String> error(String value) {
		return new Reply<>(value, ReplyType.ERROR);
	}

	public static Reply<String[]> array(String[] value) {
		return new Reply<>(value, ReplyType.ARRAY);
	}


}

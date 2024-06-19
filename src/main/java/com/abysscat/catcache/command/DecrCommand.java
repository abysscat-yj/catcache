package com.abysscat.catcache.command;

import com.abysscat.catcache.core.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.core.Reply;

/**
 * Decr command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class DecrCommand implements Command {
	@Override
	public String name() {
		return "DECR";
	}

	@Override
	public Reply<?> exec(CatCache cache, String[] args) {
		String key = getKey(args);
		return Reply.integer(cache.decr(key));
	}
}

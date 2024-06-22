package com.abysscat.catcache.command.set;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * Sadd command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/23 0:18
 */
public class SaddCommand implements Command {
	@Override
	public String name() {
		return "SADD";
	}

	@Override
	public Reply<?> exec(CatCache cache, String[] args) {
		String key = getKey(args);
		String[] vals = getParamsNoKey(args);
		return Reply.integer(cache.sadd(key, vals));
	}
}

package com.abysscat.catcache.command.list;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * Rpush command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class RpushCommand implements Command {
	@Override
	public String name() {
		return "RPUSH";
	}

	@Override
	public Reply<?> exec(CatCache cache, String[] args) {
		String key = getKey(args);
		String[] vals = getParamsNoKey(args);
		return Reply.integer(cache.rpush(key, vals));
	}
}

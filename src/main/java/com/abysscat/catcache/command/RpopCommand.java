package com.abysscat.catcache.command;

import com.abysscat.catcache.core.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.core.Reply;

/**
 * Rpop command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class RpopCommand implements Command {
	@Override
	public String name() {
		return "RPOP";
	}

	@Override
	public Reply<?> exec(CatCache cache, String[] args) {
		String key = getKey(args);
		int count = 1;
		if (args.length > 6) {
			String val = getVal(args);
			count = Integer.parseInt(val);
			return Reply.array(cache.rpop(key, count));
		}

		String[] lpop = cache.rpop(key, count);
		return Reply.bulkString(lpop == null ? null : lpop[0]);
	}
}

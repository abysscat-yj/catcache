package com.abysscat.catcache.command.common;

import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.model.Reply;

/**
 * Ping command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class PingCommand implements Command {

	@Override
	public String name() {
		return "PING";
	}

	@Override
	public Reply<?> exec(CatCache cache, String[] args) {
		String ret = "PONG";
		if(args.length >= 5) {
			ret = args[4];
		}
		return Reply.string(ret);
	}
}

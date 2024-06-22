package com.abysscat.catcache.command.common;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.Commands;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * Command command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class CommandCommand implements Command {

	@Override
	public String name() {
		return "COMMAND";
	}

	@Override
	public Reply<?> exec(CatCache cache, String[] args) {
		return Reply.array(Commands.getCommandNames());
	}
}

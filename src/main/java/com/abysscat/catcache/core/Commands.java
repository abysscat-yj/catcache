package com.abysscat.catcache.core;

import com.abysscat.catcache.command.CommandCommand;
import com.abysscat.catcache.command.DecrCommand;
import com.abysscat.catcache.command.DelCommand;
import com.abysscat.catcache.command.ExistsCommand;
import com.abysscat.catcache.command.GetCommand;
import com.abysscat.catcache.command.IncrCommand;
import com.abysscat.catcache.command.InfoCommand;
import com.abysscat.catcache.command.LindexCommand;
import com.abysscat.catcache.command.LlenCommand;
import com.abysscat.catcache.command.LpopCommand;
import com.abysscat.catcache.command.LpushCommand;
import com.abysscat.catcache.command.LrangeCommand;
import com.abysscat.catcache.command.MgetCommand;
import com.abysscat.catcache.command.MsetCommand;
import com.abysscat.catcache.command.PingCommand;
import com.abysscat.catcache.command.RpopCommand;
import com.abysscat.catcache.command.RpushCommand;
import com.abysscat.catcache.command.SetCommand;
import com.abysscat.catcache.command.StrlenCommand;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * commands factory.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:50
 */
public class Commands {

	private static final Map<String, Command> ALL = new LinkedHashMap<>();

	static {
		// 手动注册命令（不依赖spring），方便严格控制打印顺序
		initCommands();
	}

	private static void initCommands() {
		// common commands
		register(new PingCommand());
		register(new InfoCommand());
		register(new CommandCommand());

		// string commands
		register(new SetCommand());
		register(new GetCommand());
		register(new StrlenCommand());
		register(new DelCommand());
		register(new ExistsCommand());
		register(new IncrCommand());
		register(new DecrCommand());
		register(new MsetCommand());
		register(new MgetCommand());

		// list commands
		// Lpush, Rpush, Lpop, Rpop, Llen, Lindex, Lrange
		register(new LpushCommand());
		register(new LpopCommand());
		register(new RpopCommand());
		register(new RpushCommand());
		register(new LlenCommand());
		register(new LindexCommand());
		register(new LrangeCommand());

	}

	public static void register(Command command) {
		ALL.put(command.name(), command);
	}
	public static Command get(String name) {
		return ALL.get(name);
	}

	public static String[] getCommandNames() {
		return ALL.keySet().toArray(new String[0]);
	}

}

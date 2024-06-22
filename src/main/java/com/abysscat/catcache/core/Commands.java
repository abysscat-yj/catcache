package com.abysscat.catcache.core;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.command.common.CommandCommand;
import com.abysscat.catcache.command.common.InfoCommand;
import com.abysscat.catcache.command.common.PingCommand;
import com.abysscat.catcache.command.list.LindexCommand;
import com.abysscat.catcache.command.list.LlenCommand;
import com.abysscat.catcache.command.list.LpopCommand;
import com.abysscat.catcache.command.list.LpushCommand;
import com.abysscat.catcache.command.list.LrangeCommand;
import com.abysscat.catcache.command.list.RpopCommand;
import com.abysscat.catcache.command.list.RpushCommand;
import com.abysscat.catcache.command.set.SaddCommand;
import com.abysscat.catcache.command.set.ScardCommand;
import com.abysscat.catcache.command.set.SismemberCommand;
import com.abysscat.catcache.command.set.SmembersCommand;
import com.abysscat.catcache.command.set.SpopCommand;
import com.abysscat.catcache.command.set.SremCommand;
import com.abysscat.catcache.command.string.DecrCommand;
import com.abysscat.catcache.command.string.DelCommand;
import com.abysscat.catcache.command.string.ExistsCommand;
import com.abysscat.catcache.command.string.GetCommand;
import com.abysscat.catcache.command.string.IncrCommand;
import com.abysscat.catcache.command.string.MgetCommand;
import com.abysscat.catcache.command.string.MsetCommand;
import com.abysscat.catcache.command.string.SetCommand;
import com.abysscat.catcache.command.string.StrlenCommand;

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

		// common
		register(new PingCommand());
		register(new InfoCommand());
		register(new CommandCommand());

		// string
		register(new SetCommand());
		register(new GetCommand());
		register(new StrlenCommand());
		register(new DelCommand());
		register(new ExistsCommand());
		register(new IncrCommand());
		register(new DecrCommand());
		register(new MsetCommand());
		register(new MgetCommand());

		// list
		register(new LpushCommand());
		register(new LpopCommand());
		register(new RpopCommand());
		register(new RpushCommand());
		register(new LlenCommand());
		register(new LindexCommand());
		register(new LrangeCommand());

		// set
		register(new SaddCommand());
		register(new SmembersCommand());
		register(new SremCommand());
		register(new ScardCommand());
		register(new SpopCommand());
		register(new SismemberCommand());

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

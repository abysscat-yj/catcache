package com.abysscat.catcache.core;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.command.common.CommandCommand;
import com.abysscat.catcache.command.common.InfoCommand;
import com.abysscat.catcache.command.common.PingCommand;
import com.abysscat.catcache.command.hash.HdelCommand;
import com.abysscat.catcache.command.hash.HexistsCommand;
import com.abysscat.catcache.command.hash.HgetCommand;
import com.abysscat.catcache.command.hash.HgetallCommand;
import com.abysscat.catcache.command.hash.HlenCommand;
import com.abysscat.catcache.command.hash.HmgetCommand;
import com.abysscat.catcache.command.hash.HsetCommand;
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
import com.abysscat.catcache.command.zset.ZaddCommand;
import com.abysscat.catcache.command.zset.ZcardCommand;
import com.abysscat.catcache.command.zset.ZcountCommand;
import com.abysscat.catcache.command.zset.ZrankCommand;
import com.abysscat.catcache.command.zset.ZremCommand;
import com.abysscat.catcache.command.zset.ZscoreCommand;

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

		// hash
		register(new HsetCommand());
		register(new HgetCommand());
		register(new HgetallCommand());
		register(new HlenCommand());
		register(new HdelCommand());
		register(new HexistsCommand());
		register(new HmgetCommand());

		// zset
		register(new ZaddCommand());
		register(new ZcardCommand());
		register(new ZscoreCommand());
		register(new ZremCommand());
		register(new ZrankCommand());
		register(new ZcountCommand());

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

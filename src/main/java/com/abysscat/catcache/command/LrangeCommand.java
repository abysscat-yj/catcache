package com.abysscat.catcache.command;

import com.abysscat.catcache.core.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.core.Reply;

/**
 * Lrange command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class LrangeCommand implements Command {
    @Override
    public String name() {
        return "LRANGE";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        String[] params = getParamsNoKey(args);
        int start = Integer.parseInt(params[0]);
        int end = Integer.parseInt(params[1]);
        return Reply.array(cache.lrange(key, start, end));
    }
}

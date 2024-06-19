package com.abysscat.catcache.command;

import com.abysscat.catcache.core.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.core.Reply;

/**
 * Lpush command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class LpushCommand implements Command {
    @Override
    public String name() {
        return "LPUSH";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        String[] vals = getParamsNoKey(args);
        return Reply.integer(cache.lpush(key, vals));
    }
}

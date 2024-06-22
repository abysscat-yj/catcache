package com.abysscat.catcache.command.hash;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * HGET command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/23 0:18
 */
public class HgetCommand implements Command {
    @Override
    public String name() {
        return "HGET";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        String hkey = getVal(args);
        return Reply.bulkString(cache.hget(key, hkey));
    }

}

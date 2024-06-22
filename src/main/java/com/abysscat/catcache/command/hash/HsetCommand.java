package com.abysscat.catcache.command.hash;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * Hset command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/23 0:18
 */
public class HsetCommand implements Command {
    @Override
    public String name() {
        return "HSET";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        String[] hkeys = getHkeys(args);
        String[] hvals = getHvals(args);
        return Reply.integer(cache.hset(key, hkeys, hvals));
    }

}

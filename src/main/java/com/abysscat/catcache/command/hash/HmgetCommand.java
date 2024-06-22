package com.abysscat.catcache.command.hash;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * HMGET command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/23 0:18
 */
public class HmgetCommand implements Command {
    @Override
    public String name() {
        return "HMGET";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        String[] hkeys = getParamsNoKey(args);
        return Reply.array(cache.hmget(key, hkeys));
    }

}

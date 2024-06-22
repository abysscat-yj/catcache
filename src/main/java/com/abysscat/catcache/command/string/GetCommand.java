package com.abysscat.catcache.command.string;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * Get command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class GetCommand implements Command {
    @Override
    public String name() {
        return "GET";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        return Reply.bulkString(cache.get(key));
    }
}

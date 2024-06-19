package com.abysscat.catcache.command;

import com.abysscat.catcache.core.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.core.Reply;

/**
 * Exists command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class ExistsCommand implements Command {
    @Override
    public String name() {
        return "EXISTS";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String[] key = getParams(args);
        return Reply.integer(cache.exists(key));
    }
}

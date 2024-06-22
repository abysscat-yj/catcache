package com.abysscat.catcache.command.string;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * Mget command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class MgetCommand implements Command {
    @Override
    public String name() {
        return "MGET";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String[] keys = getParams(args);
        return Reply.array(cache.mget(keys));
    }
    
}

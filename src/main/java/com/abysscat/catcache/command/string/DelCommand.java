package com.abysscat.catcache.command.string;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * Del command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class DelCommand implements Command {
    @Override
    public String name() {
        return "DEL";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String[] key = getParams(args);
        return Reply.integer(cache.del(key));
    }
}

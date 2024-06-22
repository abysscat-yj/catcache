package com.abysscat.catcache.command.list;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * Llen command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class LlenCommand implements Command {
    @Override
    public String name() {
        return "LLEN";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        return Reply.integer(cache.llen(key));
    }
}

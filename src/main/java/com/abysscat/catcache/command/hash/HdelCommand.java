package com.abysscat.catcache.command.hash;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * HDEL command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/23 0:18
 */
public class HdelCommand implements Command {
    @Override
    public String name() {
        return this.getClass().getSimpleName()
                .replace("Command", "").toUpperCase();
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        String[] hkeys = getParamsNoKey(args);
        return Reply.integer(cache.hdel(key, hkeys));
    }

}

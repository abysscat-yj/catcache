package com.abysscat.catcache.command.hash;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * HGETALL command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/23 0:18
 */
public class HgetallCommand implements Command {
    @Override
    public String name() {
        return "HGETALL";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        return Reply.array(cache.hgetall(key));
    }

}

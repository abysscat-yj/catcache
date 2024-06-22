package com.abysscat.catcache.command.set;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * Smembers command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/23 0:18
 */
public class SmembersCommand implements Command {
    @Override
    public String name() {
        return "SMEMBERS";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        return Reply.array(cache.smembers(key));
    }
}

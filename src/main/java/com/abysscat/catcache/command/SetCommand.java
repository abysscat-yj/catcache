package com.abysscat.catcache.command;

import com.abysscat.catcache.core.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.core.Reply;

/**
 * Set command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class SetCommand implements Command {
    @Override
    public String name() {
        return "SET";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        String val = getVal(args);
        cache.set(key, val);
        return Reply.string(OK);
    }
}

package com.abysscat.catcache.command;

import com.abysscat.catcache.core.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.core.Reply;

/**
 * Strlen command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class StrlenCommand implements Command {
    @Override
    public String name() {
        return "STRLEN";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        return Reply.integer(cache.strlen(key));
    }
}
package com.abysscat.catcache.command.string;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

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

package com.abysscat.catcache.command.list;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * Lindex command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class LindexCommand implements Command {
    @Override
    public String name() {
        return "LINDEX";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        int index = Integer.parseInt(getVal(args));
        return Reply.bulkString(cache.lindex(key, index));
    }
}

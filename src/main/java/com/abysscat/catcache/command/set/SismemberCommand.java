package com.abysscat.catcache.command.set;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * Sismember command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/23 0:18
 */
public class SismemberCommand implements Command {
    @Override
    public String name() {
        return "SISMEMBER";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        String val = getVal(args);
        return Reply.integer(cache.sismember(key, val));
    }
}

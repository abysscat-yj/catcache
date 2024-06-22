package com.abysscat.catcache.command.set;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * Spop command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/23 0:18
 */
public class SpopCommand implements Command {
    @Override
    public String name() {
        return "SPOP";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        int count = 1;
        if (args.length > 6) {
            String val = getVal(args);
            count = Integer.parseInt(val);
            return Reply.array(cache.spop(key, count));
        }

        String[] spop = cache.spop(key, count);
        return Reply.bulkString(spop == null ? null : spop[0]);
    }
}

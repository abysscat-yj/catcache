package com.abysscat.catcache.command;

import com.abysscat.catcache.core.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.core.Reply;

/**
 * Lpop command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class LpopCommand implements Command {
    @Override
    public String name() {
        return "LPOP";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String key = getKey(args);
        int count = 1;
        if (args.length > 6) {
            String val = getVal(args);
            count = Integer.parseInt(val);
            return Reply.array(cache.lpop(key, count));
        }

        String[] lpop = cache.lpop(key, count);
        return Reply.bulkString(lpop == null ? null : lpop[0]);
    }
}

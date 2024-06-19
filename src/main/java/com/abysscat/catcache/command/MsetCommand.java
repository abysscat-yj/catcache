package com.abysscat.catcache.command;

import com.abysscat.catcache.core.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.core.Reply;

/**
 * MSet command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class MsetCommand implements Command {
    @Override
    public String name() {
        return "MSET";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        String[] keys = getKeys(args);
        String[] vals = getVals(args);
        cache.mset(keys, vals);
        return Reply.string(OK);
    }

}

package com.abysscat.catcache.command.string;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

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

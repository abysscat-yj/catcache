package com.abysscat.catcache.command.common;

import com.abysscat.catcache.command.Command;
import com.abysscat.catcache.core.CatCache;
import com.abysscat.catcache.model.Reply;

/**
 * Info command.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/20 0:54
 */
public class InfoCommand implements Command {

    private static final String INFO = "CatCache Server[v1.0.0], created by abysscat." + CRLF
            + "Mock Redis Server, at 2024-06-19 in Beijing." + CRLF;


    @Override
    public String name() {
        return "INFO";
    }

    @Override
    public Reply<?> exec(CatCache cache, String[] args) {
        return Reply.bulkString(INFO);
    }
}

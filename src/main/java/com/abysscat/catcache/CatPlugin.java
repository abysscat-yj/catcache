package com.abysscat.catcache;

/**
 * catcache plugin interface.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/13 0:15
 */
public interface CatPlugin {

	void init();

	void startup();

	void shutdown();

}

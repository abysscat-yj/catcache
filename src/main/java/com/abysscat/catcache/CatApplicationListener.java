package com.abysscat.catcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * catcache plugins entrypoint.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/13 0:16
 */
@Component
public class CatApplicationListener implements ApplicationListener<ApplicationEvent> {

	@Autowired
	List<CatPlugin> plugins;

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof ApplicationReadyEvent) {
			// 启动插件
			for (CatPlugin plugin : plugins) {
				plugin.init();
				plugin.startup();
			}
		} else if (event instanceof ContextClosedEvent) {
			// 关闭插件
			for (CatPlugin plugin : plugins) {
				plugin.shutdown();
			}
		}
	}
}

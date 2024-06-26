package com.abysscat.catcache.core;

import com.abysscat.catcache.CatPlugin;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.stereotype.Component;

/**
 * catcache server.
 *
 * 作为插件统一管理
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/13 0:35
 */
@Component
public class CatCacheServer implements CatPlugin {

	int port = 6379;

	EventLoopGroup bossGroup;
	EventLoopGroup workerGroup;
	Channel channel;

	@Override
	public void init() {
		// 负责接收客户端的连接请求，将已建立的连接分发给 WorkerGroup 进行后续的数据处理
		bossGroup = new NioEventLoopGroup(1,    new DefaultThreadFactory("catcache-redis-boss"));

		// 负责处理已经建立连接的数据读写操作，多线程处理多个连接
		workerGroup = new NioEventLoopGroup(16, new DefaultThreadFactory("catcache-redis-work"));
	}

	@Override
	public void startup() {
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.TCP_NODELAY, true)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childOption(ChannelOption.SO_REUSEADDR, true)
					.childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
					.childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
					.childOption(EpollChannelOption.SO_REUSEPORT, true)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.DEBUG))
					.childHandler(new ChannelInitializer<SocketChannel>(){
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new CatCacheDecoder());
							ch.pipeline().addLast(new CatCacheHandler());
						}
					});

			channel = b.bind(port).sync().channel();
			System.out.println("开启 mock netty redis 服务器，端口为 " + port);
			channel.closeFuture().sync();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	@Override
	public void shutdown() {
		if(this.channel != null) {
			this.channel.close();
			this.channel = null;
		}
		if(this.bossGroup != null) {
			this.bossGroup.shutdownGracefully();
			this.bossGroup = null;
		}
		if(this.workerGroup != null) {
			this.workerGroup.shutdownGracefully();
			this.workerGroup = null;
		}
	}
}

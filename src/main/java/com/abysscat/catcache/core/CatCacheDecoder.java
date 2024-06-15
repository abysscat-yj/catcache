package com.abysscat.catcache.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * catcache decoder.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/13 0:39
 */
public class CatCacheDecoder extends ByteToMessageDecoder {

	AtomicLong counter = new AtomicLong();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		System.out.println("CatCacheDecoder count:" + counter.incrementAndGet());

		if (in.readableBytes() <= 0) return;
		int count = in.readableBytes();
		int index = in.readerIndex();
		System.out.println("read count:" + count + ",index:" + index);

		byte[] bytes = new byte[count];
		in.readBytes(bytes);
		String ret = new String(bytes);
		System.out.println("read ret: " + ret);

		out.add(ret);
	}
}

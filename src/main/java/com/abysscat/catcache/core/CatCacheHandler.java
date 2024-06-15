package com.abysscat.catcache.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * catcache handler.
 *
 * @Author: abysscat-yj
 * @Create: 2024/6/13 0:40
 */
public class CatCacheHandler extends SimpleChannelInboundHandler<String> {

	/**
	 * 五种基本协议类型前缀
	 */
	private static final String STR_PREFIX = "+";
	private static final String ERROR_PREFIX = "-";
	private static final String INT_PREFIX = ":";
	private static final String BULK_PREFIX = "$";
	private static final String ARRAY_PREFIX = "*";

	private static final String CRLF = "\r\n";
	private static final String OK = "OK";
	private static final String INFO = "CatCache Server[v1.0.0], created by abysscat." + CRLF
			+ "Mock Redis Server, at 2024-06-13 in Beijing." + CRLF;

	public static final CatCache cache = new CatCache();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
								String message) throws Exception {

		String[] args = message.split(CRLF);
		System.out.println("CatCacheHandler => " + String.join(",", args));
		String cmd = args[2].toUpperCase();

		switch (cmd) {
			// *1,$7,COMMAND
			case "COMMAND" -> writeByteBuf(ctx, "*2"
					+ CRLF + "$7"
					+ CRLF + "COMMAND"
					+ CRLF + "$4"
					+ CRLF + "PING"
					+ CRLF);
			// *1,$4,PING
			// *2,$4,PING,$3,abc
			case "PING" -> {
				String ret = "PONG";
				if (args.length >= 5) {
					ret = args[4];
				}
				simpleString(ctx, ret);
			}
			// *1,$4,INFO
			case "INFO" -> bulkString(ctx, INFO);
			// *2,$3,get,$1,a
			case "GET" -> {
				String value = cache.get(args[4]);
				bulkString(ctx, value);
			}
			// *3,$3,set,$1,a,$3,111
			case "SET" -> {
				cache.set(args[4], args[6]);
				simpleString(ctx, OK);
			}
			// *2,$6,strlen,$1,a
			case "STRLEN" -> {
				String value = cache.get(args[4]);
				integer(ctx, value == null ? 0 : value.length());
			}
			// *3,$3,del,$1,a,$1,b
			case "DEL" -> {
				int len = (args.length - 3) / 2;
				String[] keys = new String[len];
				for (int i = 0; i < len; i++) {
					keys[i] = args[4 + i * 2];
				}
				int del = cache.del(keys);
				integer(ctx, del);
			}
			// *4,$6,exists,$1,a,$1,b,$1,c
			case "EXISTS" -> {
				int len = (args.length - 3) / 2;
				String[] keys = new String[len];
				for (int i = 0; i < len; i++) {
					keys[i] = args[4 + i * 2];
				}
				integer(ctx, cache.exists(keys));
			}
			// *4,$4,mget,$1,a,$1,b,$1,c
			case "MGET" -> {
				int len = (args.length - 3) / 2;
				String[] keys = new String[len];
				for (int i = 0; i < len; i++) {
					keys[i] = args[4 + i * 2];
				}
				array(ctx, cache.mget(keys));
			}
			// *5,$4,mset,$1,a,$3,111,$1,b,$3,222
			case "MSET" -> {
				int len = (args.length - 3) / 4;
				String[] keys = new String[len];
				String[] vals = new String[len];
				for (int i = 0; i < len; i++) {
					keys[i] = args[4 + i * 4];
					vals[i] = args[6 + i * 4];
				}
				cache.mset(keys, vals);
				simpleString(ctx, OK);
			}
			// *2,$4,incr,$1,a
			case "INCR" -> {
				String key = args[4];
				try {
					integer(ctx, cache.incr(key));
				} catch (NumberFormatException nfe) {
					error(ctx, "NFE " + key + " value[" + cache.get(key) + "] is not an integer.");
				}
			}
			// *2,$4,decr,$1,a
			case "DECR" -> {
				String key = args[4];
				try {
					integer(ctx, cache.decr(key));
				} catch (NumberFormatException nfe) {
					error(ctx, "NFE " + key + " value is not an integer.");
				}
			}
			default -> simpleString(ctx, OK);
		}
	}

	private void error(ChannelHandlerContext ctx, String msg) {
		writeByteBuf(ctx, errorEncode(msg));
	}

	private static String errorEncode(String msg) {
		return "-" + msg + CRLF;
	}

	private void integer(ChannelHandlerContext ctx, int i) {
		writeByteBuf(ctx, integerEncode(i));
	}

	private static String integerEncode(int i) {
		return ":" + i + CRLF;
	}

	private void array(ChannelHandlerContext ctx, String[] array) {
		writeByteBuf(ctx, arrayEncode(array));
	}

	private static String arrayEncode(Object[] array) {
		StringBuilder sb = new StringBuilder();
		if (array == null) {
			sb.append("*-1" + CRLF);
		} else if (array.length == 0) {
			sb.append("*0" + CRLF);
		} else {
			sb.append("*" + array.length + CRLF);
			for (int i = 0; i < array.length; i++) {
				Object obj = array[i];
				if (obj == null) {
					sb.append("$-1" + CRLF);
				} else {
					if (obj instanceof Integer) {
						sb.append(integerEncode((Integer) obj));
					} else if (obj instanceof String) {
						sb.append(bulkStringEncode((String) obj));
					} else if (obj instanceof Object[] objs) {
						sb.append(arrayEncode(objs));
					}
				}
			}
		}
		return sb.toString();
	}

	private void bulkString(ChannelHandlerContext ctx, String content) {
		writeByteBuf(ctx, bulkStringEncode(content));
	}

	private static String bulkStringEncode(String content) {
		String ret;
		if (content == null) {
			ret = "$-1";
		} else if (content.isEmpty()) {
			ret = "$0";
		} else {
			ret = BULK_PREFIX + content.getBytes().length + CRLF + content;
		}
		return ret + CRLF;
	}

	private void simpleString(ChannelHandlerContext ctx, String content) {
		writeByteBuf(ctx, stringEncode(content));
	}

	private static String stringEncode(String content) {
		String ret;
		if (content == null) {
			ret = "$-1";
		} else if (content.isEmpty()) {
			ret = "$0";
		} else {
			ret = STR_PREFIX + content;
		}
		return ret + CRLF;
	}

	private void writeByteBuf(ChannelHandlerContext ctx, String content) {
		System.out.println("wrap byte buffer and reply: " + content);
		ByteBuf buffer = Unpooled.buffer(128);
		buffer.writeBytes(content.getBytes());
		ctx.writeAndFlush(buffer);
	}
}

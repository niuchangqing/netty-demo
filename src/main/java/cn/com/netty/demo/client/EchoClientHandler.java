package cn.com.netty.demo.client;

import cn.com.netty.demo.utils.Log;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
* @author Niucqing
* @email niucqing@gmail.com
* @version 创建时间：2017年3月23日 下午6:39:30
* 类说明
*/
public class EchoClientHandler extends ChannelHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Log.log("client received resp", msg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 100; i++) {
			ByteBuf buf = Unpooled.copiedBuffer("hello world!||".getBytes());
			ctx.writeAndFlush(buf);
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Log.log(cause);
		ctx.close();
	}
}

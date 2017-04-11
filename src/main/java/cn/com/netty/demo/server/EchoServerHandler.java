package cn.com.netty.demo.server;

import java.net.SocketAddress;

import cn.com.netty.demo.utils.Log;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**
* @author Niucqing
* @email  niucqing@gmail.com
* @version 创建时间：2017年3月23日 下午5:55:33
* 类说明
*/
public class EchoServerHandler extends ChannelHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String message = (String) msg;
		Log.log("server received msg is", message);
		message = "echo " + message + "||";
		ByteBuf buf = Unpooled.copiedBuffer(message.getBytes());
		ctx.writeAndFlush(buf);
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		Log.log("client", promise.get(), "disconnected...");
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {

		Log.log("client" + remoteAddress.toString() + "connected...");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		Log.log(cause);
	}

}

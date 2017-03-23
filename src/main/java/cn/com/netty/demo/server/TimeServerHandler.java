package cn.com.netty.demo.server;

import org.joda.time.DateTime;

import cn.com.netty.demo.utils.Log;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
* @author Niucqing
* @email niucqing@gmail.com
* @version 创建时间：2017年3月22日 下午7:58:54
* 类说明
*/
public class TimeServerHandler extends ChannelHandlerAdapter {
	private static int count = 0;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		Log.log("time server received message:", body, "，the counter is", ++count);

		String time = DateTime.now().toString("yyyy-MM-dd hh:mm:ss SSS"+System.lineSeparator());
		Log.log("resp:", time);
		
		ByteBuf resp = Unpooled.copiedBuffer(time.getBytes());
		ctx.writeAndFlush(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}

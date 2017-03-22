package cn.com.netty.demo.server;

import java.io.UnsupportedEncodingException;

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
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		toString(msg);
		String time = DateTime.now().toString("yyyy-MM-dd");
		Log.log("resp:", time);
		ByteBuf resp = Unpooled.copiedBuffer(time.getBytes());
		ctx.write(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}

	private String toString(Object msg) throws UnsupportedEncodingException {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String message = new String(req, "utf-8");
		Log.log("netty server receive", message);
		return null;

	}

}

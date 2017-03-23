package cn.com.netty.demo.client;

import cn.com.netty.demo.utils.Log;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
* @author Niucqing
* @email niucqing@gmail.com
* @version 创建时间：2017年3月23日 上午11:15:50
* 类说明
*/
public class TimeClientHandler extends ChannelHandlerAdapter {
	private static int c = 0;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String resp = (String) msg;
		Log.log("client received from server:", resp, "the counter is:", ++c);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		int count = 0;
		ByteBuf buf = null;
		while (count <= 100) {
			++count;
			String req = "client send message to server : msg-" + count + System.lineSeparator();
			byte[] b = req.getBytes();
			buf = Unpooled.buffer(b.length);
			buf.writeBytes(b);
			ctx.writeAndFlush(buf);
		}
		Log.log("发送结束");
	}
}

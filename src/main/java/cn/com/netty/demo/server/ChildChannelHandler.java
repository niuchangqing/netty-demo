package cn.com.netty.demo.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
* @author Niucqing
* @email niucqing@gmail.com
* @version 创建时间：2017年3月22日 下午7:55:18
* 类说明
*/
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
		ch.pipeline().addLast(new StringDecoder());
		ch.pipeline().addLast(new TimeServerHandler());
	}

}

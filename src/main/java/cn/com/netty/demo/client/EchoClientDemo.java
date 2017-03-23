package cn.com.netty.demo.client;

import cn.com.netty.demo.utils.Log;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
* @author Niucqing
* @email niucqing@gmail.com
* @version 创建时间：2017年3月23日 下午5:30:10
* 类说明
*/
public class EchoClientDemo {

	public static void main(String[] args) throws InterruptedException {
		start(8088);
	}

	public static void start(int port) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap boot = new Bootstrap();
			boot.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ByteBuf delimiter = Unpooled.copiedBuffer("||".getBytes());
							ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter))
									.addLast(new StringDecoder()).addLast(new EchoClientHandler());
						}
					});
			ChannelFuture f = boot.connect("localhost",port).sync();

			Log.log("client started...");
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			Log.log(e);
		} finally {
			group.shutdownGracefully();
		}
	}

}

package cn.com.netty.demo.server;

import cn.com.netty.demo.utils.Log;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
* @author Niucqing
* @email niucqing@gmail.com
* @version 创建时间：2017年3月23日 下午5:30:25
* 类说明
*/
public class EchoServerDemo {

	public static void main(String[] args) throws InterruptedException {
		start(8088);
	}

	public static void start(int port) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap boot = new ServerBootstrap();
			boot.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ByteBuf buf = Unpooled.copiedBuffer("||".getBytes());
							ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
							ch.pipeline().addLast(new StringDecoder());
							ch.pipeline().addLast(new EchoServerHandler());
						};
					});
			ChannelFuture f = boot.bind(port).sync();
			Log.log("server started...");
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			Log.log(e);
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}

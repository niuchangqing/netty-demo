package cn.com.netty.demo.server;

import cn.com.netty.demo.utils.Log;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
* @author Niucqing
* @email niucqing@gmail.com
* @version 创建时间：2017年3月22日 下午7:37:47
* 类说明
*/
public class TimeServer {
	private static final int PORT = 8088;

	public void init() throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap boot = new ServerBootstrap();
			boot.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChildChannelHandler());

			ChannelFuture future = boot.bind(PORT).sync();
			
			Log.log("time server started...");
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			Log.log(e);
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new TimeServer().init();
	}

}

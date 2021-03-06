package cn.com.netty.demo.client;

import cn.com.netty.demo.utils.Log;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
* @author Niucqing
* @email niucqing@gmail.com
* @version 创建时间：2017年3月22日 下午7:37:57
* 类说明
*/
public class TimeClient {

	public static void main(String[] args) throws InterruptedException {
		start();
	}

	public static void start() throws InterruptedException {
		EventLoopGroup client = new NioEventLoopGroup();

		try {
			Bootstrap boot = new Bootstrap();
			boot.group(client).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new LineBasedFrameDecoder(1024)).addLast(new StringDecoder())
									.addLast(new TimeClientHandler());
						}
					});
			ChannelFuture future = boot.connect("127.0.0.1", 8088).sync();
			Log.log("client is connected ...");
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			Log.log(e);
		} finally {
			client.shutdownGracefully();
		}
	}

}

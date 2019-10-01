package cn.fmnx.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/1 14:13
 * @version: netty客户端
 * @modified By:
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //1.创建一个EventLoopGroup线程组
        EventLoopGroup group = new NioEventLoopGroup();
        //2.创建客户端启动助手
        Bootstrap b = new Bootstrap();
        b.group(group)//3.设置EventLoopGroup线程组
                .channel(NioSocketChannel.class)//4.使用nioServerSocketChannel作为客户端通道实现
                .handler(new ChannelInitializer<SocketChannel>() {//5.创建一个通道初始化对象
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {//6.往 Pipeline 链中添加自定义的业务处理 handler
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                        System.out.println("--------client is ready---------");
                    }
                });
        //7.启动客户端，等待连上服务器端(非阻塞)
        ChannelFuture future = b.connect("127.0.0.1", 9999).sync();
        //8.等待连接关闭
        future.channel().closeFuture().sync();
    }
}

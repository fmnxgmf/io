package cn.fmnx.netty.server;

import cn.fmnx.protobuf.BookMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/1 13:54
 * @version: netty服务端
 * @modified By:
 */
public class NettyServer {
    public static void main(String[] args) throws Exception{
        //1.创建一个线程组:用来处理网络事件（接受客户端连接）
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //2.创建一个线程组:用来处理网络事件(处理io通道操作)
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //3.创建服务器端启动助手配置参数
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup,workerGroup)//4.设置连个线程组EventLoopGroup
        .channel(NioServerSocketChannel.class)//5.使用nioServerSocketChannel作为服务器端通道实现
        .option(ChannelOption.SO_BACKLOG,128)//6.设置线程队列中等待连接数
        .childOption(ChannelOption.SO_KEEPALIVE,true)//7.保持活动的连接状态
        .childHandler(new ChannelInitializer<SocketChannel>() {//8.创建一个通道的初始化对象
            @Override
            protected void initChannel(SocketChannel sc) throws Exception {//9.往Pipeline链中添加自定义的业务处理handler
                sc.pipeline().addLast("decoder",new ProtobufDecoder(BookMessage.Book.getDefaultInstance()));
                sc.pipeline().addLast(new NettyServerHandler());//服务器端的业务处理类
                System.out.println("---------server is ready---------");
            }
        });
        //10.启动服务器端并绑定端口，等待接收客户端连接(非阻塞)
        ChannelFuture cf = b.bind(9999).sync();
        System.out.println("----------server is starting----------");

        //11.关闭通道，关闭线程池
        cf.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

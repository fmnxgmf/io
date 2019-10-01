package cn.fmnx.chat.chatClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/1 15:13
 * @version:
 * @modified By:
 */
public class ChatClient {
    private final String host;
    private final int port;

    public ChatClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder",new StringDecoder());
                            pipeline.addLast("encoder",new StringEncoder());
                            pipeline.addLast("handler",new ChatClientHandler());
                        }
                    });
            Channel channel = b.connect(host, port).sync().channel();
            System.out.println("-------------"+channel.localAddress().toString().substring(1)+"---------------");
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()){
                String s = sc.nextLine();
                channel.writeAndFlush(s+"\r\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new ChatClient("127.0.0.1",9999).run();
    }
}

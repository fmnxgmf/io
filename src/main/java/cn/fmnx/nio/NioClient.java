package cn.fmnx.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/9/30 15:14
 * @version: nio客户端
 * @modified By:
 */
public class NioClient {

    public static void main(String[] args) throws Exception{
        //1.得到一个网络通道
        SocketChannel channel = SocketChannel.open();
        //2.设置非阻塞模式
        channel.configureBlocking(false);
        //3.提供服务器端的ip和端口号
        InetSocketAddress address = new InetSocketAddress("127.0.0.1",9999);
        //4.连接服务器端
        if(!channel.connect(address)){
            while (!channel.finishConnect()){//nio非阻塞
                System.out.println("Client:连接服务器端的同时，我还可以干别的一些事情");
            }
        }
        //5.得到一个缓冲区并存入数据库
        String msg = "hello ,server";
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        //6.发送数据
        channel.write(buffer);
        System.in.read();
    }
}

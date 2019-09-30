package cn.fmnx.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/9/30 14:47
 * @version: nio服务端
 * @modified By:
 */
public class NioServer {
    public static void main(String[] args) throws Exception{
        //1.得到一个serverSocketChanner对象
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2.得到一个selector对象
        Selector selector = Selector.open();
        //3.绑定一个端口号
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //4.设置非阻塞方式
        serverSocketChannel.configureBlocking(false);
        //5.把serverSocketChannel对象注册给selector对象
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //6.干活了
        while (true){
            //6.1监听客户端
            if(selector.select(2000)==0){//nio非阻塞优势
                System.out.println("server:没有客户端搭理我，我就干点别的事");
                continue;
            }
            //6.2得到selectionkey判断通道里的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                if(key.isAcceptable()){//客户端连接请求事件
                    System.out.println("OP_ACCEPT");
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(key.isReadable()){//读取客户端数据事件
                    SocketChannel channel = (SocketChannel)key.channel();
                    ByteBuffer buffer =  (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    String substring = channel.getLocalAddress().toString().substring(1);
                    System.out.println(substring+"客户端发来数据："+new String(buffer.array()));
                }
                //6.3手动从集合中删除当前key，防止重复处理
                iterator.remove();
            }
        }
    }
}

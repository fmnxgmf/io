package cn.fmnx.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/1 9:32
 * @version:
 * @modified By:
 */
public class AioServer {
    public AioServer(int port)throws Exception{
        final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(port));
        listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

            @Override
            public void completed(AsynchronousSocketChannel ch, Object attachment) {
                listener.accept(null,this);
                handler(ch);
                write(ch);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("异步io失败");
            }
        });
    }
    //真正逻辑
    public void handler(AsynchronousSocketChannel ch) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try{
            ch.read(buffer);

        }catch (Exception e){
            e.printStackTrace();
        }
        buffer.flip();
        System.out.println("服务端接受:"+buffer.get());
    }

    public void write(AsynchronousSocketChannel ch){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("哈佛似懂非懂".getBytes());
        buffer.flip();
        ch.write(buffer);

    }
    public static void main(String[] args) throws Exception{
        AioServer server = new AioServer(7080);
        System.out.println("监听客户端");
        Thread.sleep(1000000);
    }
}

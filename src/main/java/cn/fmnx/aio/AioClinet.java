package cn.fmnx.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/1 9:32
 * @version:
 * @modified By:
 */
public class AioClinet {
    private AsynchronousSocketChannel client = null;
    public AioClinet(String host,int port)throws Exception{
        client = AsynchronousSocketChannel.open();
        Future<Void> future = client.connect(new InetSocketAddress(host, port));
        System.out.println(future.get());
    }
    public void write(byte b){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(b);
        buffer.flip();
        client.write(buffer);
    }
    public static void main(String[] args) throws Exception{
        AioClinet clinet = new AioClinet("127.0.0.1",7080);
        clinet.write((byte) 22);
    }
}

package cn.fmnx.nio.chat;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/9/30 15:47
 * @version: nio多人聊天服务端
 * @modified By:
 */
public class ChatServer {
    private Selector selector;
    private ServerSocketChannel listenerChannel;
    private static final int PORT = 9999;

    public ChatServer(){
        try {
            //得到选择器
            selector = Selector.open();
            //打开监听通道
            listenerChannel = ServerSocketChannel.open();
            //绑定端口
            listenerChannel.bind(new InetSocketAddress(PORT));
            //设置非阻塞
            listenerChannel.configureBlocking(false);
            //将选择器绑定到监听通道并监听accept事件
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT);
            printInfo("chat server is ready");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start(){
       try {
           while (true){//不停轮询
               int count = selector.select();//获取就绪channer
               if(count>0){
                   Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                   while (iterator.hasNext()){
                       SelectionKey key = iterator.next();
                       //监听到accept
                       if(key.isAcceptable()){
                           SocketChannel socketChannel = listenerChannel.accept();
                           //非阻塞模式
                           socketChannel.configureBlocking(false);
                           //注册到选择器上并监听read
                           socketChannel.register(selector,SelectionKey.OP_READ);
                           System.out.println(socketChannel.getRemoteAddress().toString().substring(1)+"上线了...");
                           //将此对应的channer设置为accept接着准备接收其它客户请求
                           key.interestOps(SelectionKey.OP_ACCEPT);
                       }
                       //监听read
                       if(key.isReadable()){
                           readMsg(key);//读取客户端发来的数据
                       }
                       iterator.remove();
                   }
               }
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public void readMsg(SelectionKey key) {
        SocketChannel channel = null;
        try{
            //得到关联的通道
            channel = (SocketChannel)key.channel();
            //设置buffer缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //从通道中读取数据存到缓冲区
            int count = channel.read(buffer);
            if(count>0){
                //把缓冲区数据转换成字符串
                String msg = new String(buffer.array());
                printInfo(msg);
                //将关联的channel设置为read，继续准备接收数据
                key.interestOps(SelectionKey.OP_READ);
                BroadCast(channel,msg);//向所有客户端广播数据
            }
            buffer.clear();
        }catch (Exception e){
            try{
                //当客户端关闭channer时，进行异常处理
                printInfo(channel.getRemoteAddress().toString().substring(1)+"下线了...");
                key.cancel();//取消注册
                channel.close();//关闭通道
            }catch (Exception es){
                es.printStackTrace();
            }
        }
    }

    public void BroadCast(SocketChannel channel, String msg) throws Exception{
        System.out.println("发送广播...");
        //广播数据到所有的socketChannel中
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            //排除自身
            if(targetChannel instanceof SocketChannel && targetChannel!=channel){
                SocketChannel dest = (SocketChannel) targetChannel;
                //把数据存到缓冲区
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //往通道中写数据
                dest.write(buffer);
            }
        }
    }

    public void printInfo(String string) {//网控制台打印消息
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        System.out.println("["+sdf.format(new Date())+"]->"+string);
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }
}

package cn.fmnx.nio.chat;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/9/30 15:47
 * @version: nio多人聊天客户端
 * @modified By:
 */
public class ChatClient {
    private final String HOST = "127.0.0.1";
    private int PORT = 9999;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    public ChatClient() throws Exception{
//        //得到选择器
//        selector = Selector.open();
//        //连接远程服务器
//         socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
//         //设置非阻塞
//        socketChannel.configureBlocking(false);
//        //注册选择器并设置为read
//        socketChannel.register(selector, SelectionKey.OP_READ);
        //1. 得到一个网络通道
        socketChannel=SocketChannel.open();
        //2. 设置非阻塞方式
        socketChannel.configureBlocking(false);
        //3. 提供服务器端的IP地址和端口号
        InetSocketAddress address=new InetSocketAddress(HOST,PORT);
        //4. 连接服务器端
        if(!socketChannel.connect(address)){
            while(!socketChannel.finishConnect()){  //nio作为非阻塞式的优势
                System.out.println("Client:连接服务器端的同时，我还可以干别的一些事情");
            }
        }
        //得到客户端ip地址和端口信息作为聊天的用户名
        System.out.println("*********"+socketChannel.getLocalAddress().toString()+"**********");
        userName = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println("---------------Client(" + userName + ") is ready---------------");
    }
    //向服务端发送信息
    public void sendMsg(String msg)throws Exception{
        //如果控制台输入bye就关闭通道，接收聊天
        if(msg.equalsIgnoreCase("bye")){
            socketChannel.close();
            socketChannel = null;
            return;
        }
        msg = userName+"说:"+msg;
        try {
            //网通道中写数据
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //从服务端读数据
    public  void  reciveMsg(){
        try{
//            int readChanners = selector.select();
//            if(readChanners>0){//有通道可以用
//                Set<SelectionKey> selectionKeys = selector.selectedKeys();
//                Iterator<SelectionKey> iterator = selectionKeys.iterator();
//                while (iterator.hasNext()){
//                    SelectionKey key = iterator.next();
//                    if(key.isReadable()){
//                        //得到想关联的通道
//                        socketChannel = (SocketChannel)key.channel();
//                        //得到一个缓冲区
//                        ByteBuffer buffer = ByteBuffer.allocate(1024);
//                        //读取数据并存到缓冲区
//                        socketChannel.read(buffer);
//                        //把缓冲区数据转换成字符串
//                        String msg = new String(buffer.array());
//                        System.out.println(msg.trim());
//                    }
//                    iterator.remove(); //删除当前key，防止重复处理
//                }
//            }else {
//                System.out.println("人呢？都去哪儿了？没人聊天啊...");
//            }
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int size=socketChannel.read(buffer);
            if(size>0){
                String msg=new String(buffer.array());
                System.out.println(msg.trim());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

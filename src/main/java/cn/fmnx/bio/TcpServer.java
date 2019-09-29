package cn.fmnx.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/9/29 21:14
 * @version: 其余tcp的bio
 * @modified By:
 */
public class TcpServer {
    public static void main(String[] args) throws IOException {
        //1.创建serversocket对象
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true){
            //2.监听客户端
            System.out.println("来呀1");
            Socket clietSocket = serverSocket.accept();//阻塞
            System.out.println("来呀2");
            //3.从连接中取出输入流来接收消息
            InputStream inputStream = clietSocket.getInputStream();//阻塞
            byte[] b = new byte[1024];
            inputStream.read(b);
            String hostAddress = clietSocket.getInetAddress().getHostAddress();
            System.out.println(hostAddress+"说:"+new String(b).trim());
            //4.从连接中取出输出流并回话
            OutputStream outputStream = clietSocket.getOutputStream();
            outputStream.write("没有钱呀".getBytes());
            //5.关闭
            clietSocket.close();
        }
    }
}

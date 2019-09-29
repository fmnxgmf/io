package cn.fmnx.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/9/29 21:24
 * @version: bio的客户端测试
 * @modified By:
 */
public class TcpClient {

    public static void main(String[] args) throws Exception{
        while (true){
            //1.创建soket对象
            Socket socket = new Socket("127.0.0.1",9999);
            //2.从连接中取出输出流并发消息
            OutputStream out = socket.getOutputStream();
            System.out.println("请输入:");
            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            out.write(s.getBytes());
            //3.从连接中取出输入流并接收回话
            InputStream is = socket.getInputStream();
            byte[] b = new byte[1024];
            is.read(b);
            System.out.println("老板说:"+new String(b).trim());
            //4.关闭
            socket.close();

        }
    }
}

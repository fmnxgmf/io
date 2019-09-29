package cn.fmxn.nio;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/9/29 21:44
 * @version: 采用nio模式来文件的读写复制
 * @modified By:
 */
public class NioDemo {
    //往本地文件中写数据
    @Test
    public void test1() throws Exception {
        String meg = "hello noi 文本文件";
        FileOutputStream fos = new FileOutputStream("demo.txt");
        FileChannel channel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(meg.getBytes());
        buffer.flip();
        channel.write(buffer);
        fos.close();
    }
    //从本地文件中读数据
    @Test
    public void test2() throws Exception{
        File file = new File("demo.txt");
        FileInputStream fis = new FileInputStream(file);
        FileChannel channel = fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        channel.read(buffer);
        System.out.println(new String(buffer.array()));
        fis.close();
    }
    //bio复制文件
    @Test
    public void test3() throws Exception{
        FileInputStream fis = new FileInputStream("E:\\IDEA_workplace\\io\\demo.txt");
        FileOutputStream fos = new FileOutputStream("E:\\IDEA_workplace\\io\\demo2.txt");
        byte[] b = new byte[1024];
        while (true){
            int i = fis.read(b);
            if(i==-1){
                break;
            }
            fos.write(b,0,i);
        }
        fis.close();
        fos.close();
    }
    //nio复制文件
    @Test
    public void test4() throws Exception{
        FileInputStream fis = new FileInputStream("E:\\IDEA_workplace\\io\\demo.txt");
        FileOutputStream fos = new FileOutputStream("E:\\IDEA_workplace\\io\\demo3.txt");
        FileChannel sourceCh = fis.getChannel();
        FileChannel destCh = fos.getChannel();
        //从目标通道中复制数据到当前通道
        //destCh.transferFrom(sourceCh,0,sourceCh.size());
        //把数据从当前通道复制给目标通道
        sourceCh.transferTo(0,sourceCh.size(),destCh);
        sourceCh.close();
        destCh.close();
    }

}

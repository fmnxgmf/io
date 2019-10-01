package cn.fmnx.nio.chat;

import java.util.Scanner;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/9/30 17:15
 * @version: 测试
 * @modified By:
 */
public class TestChat {
    public static void main(String[] args) throws Exception {
        //创建一个聊天客户端
        final ChatClient chatClient = new ChatClient();
        new Thread(){// //单独开一个线程不断的接收服务器端广播的数据
            @Override
            public void run(){
                while (true){
                    chatClient.reciveMsg();
                    try{
                        Thread.sleep(2000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()){
            String s = sc.nextLine();
            chatClient.sendMsg(s);
        }
    }
}

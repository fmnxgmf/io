package client;

import hello.HelloService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/2 11:06
 * @version:
 * @modified By:
 */
public class HelloClientDemo {
    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;

    public void startClient(String userName){
        TTransport tTransport = null;
        try{
            tTransport = new TSocket(SERVER_IP,SERVER_PORT,TIMEOUT);
            //协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(tTransport);
            // TProtocol protocol = new TCompactProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            HelloService.Client client = new HelloService.Client(protocol);
            tTransport.open();
            String result = client.helloString("陈智超 和 wl");
            System.out.println("Thrify client result =:"+result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null !=tTransport){
                tTransport.close();
            }
        }

    }

    public static void main(String[] args) {
        HelloClientDemo client = new HelloClientDemo();
        client.startClient("china");
    }
}

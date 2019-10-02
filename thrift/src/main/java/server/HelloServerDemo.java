package server;

import hello.HelloService;
import hello.HelloServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/2 10:49
 * @version:
 * @modified By:
 */
public class HelloServerDemo {
    public static final int SERVER_PORT = 8090;
    public void startServer(String userName){
        try {
            System.out.println("HelloWorld TsimpleServer start ...");
            TProcessor tProcessor = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());
            //简单的单线程服务模型
            TServerSocket serverSocket = new TServerSocket(SERVER_PORT);
            TServer.Args tArgs = new TServer.Args(serverSocket);
            tArgs.processor(tProcessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TSimpleServer(tArgs);
            server.serve();
        }catch (Exception e){
            System.out.println("Servet start error!!!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HelloServerDemo server = new HelloServerDemo();
        server.startServer("china");
    }
}

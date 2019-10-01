package cn.fmnx.rpc.server;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/1 21:43
 * @version:
 * @modified By:
 */
public class HelloRPCImpl implements HelloRPC {

    @Override
    public String hello(String name) {
        return "hello"+name;
    }
}

package cn.fmnx.rpc.server;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/1 21:40
 * @version:
 * @modified By:
 */
public class HelloNettyImpl implements HelloNetty{

    @Override
    public String hello() {
        return "hello netty";
    }
}

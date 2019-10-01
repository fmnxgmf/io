package cn.fmnx.rpc.client;

import cn.fmnx.rpc.clientStub.NettyRPCProxy;


/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/1 22:38
 * @version:
 * @modified By:
 */
public class TestRPC {
    public static void main(String[] args) {
        HelloNetty helloNetty = (HelloNetty) NettyRPCProxy.create(HelloNetty.class);
        System.out.println(helloNetty.hello());

        HelloRPC helloRPC = (HelloRPC) NettyRPCProxy.create(HelloRPC.class);
        System.out.println(helloRPC.hello("czc"));
    }


}

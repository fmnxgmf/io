package cn.fmnx.rpc.clientStub;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/1 22:25
 * @version: 客户端业务处理类
 * @modified By:
 */
public class ResultHandler extends ChannelInboundHandlerAdapter {
    private Object response;
    public Object  getResponse(){
        return response;
    }

    @Override//读取服务器端返回的数据(远程调用的结果)
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       response = msg;
       ctx.close();
    }
}

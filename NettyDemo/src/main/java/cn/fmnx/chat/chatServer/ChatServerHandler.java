package cn.fmnx.chat.chatServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/1 14:40
 * @version:
 * @modified By:
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    public static List<Channel> channels = new ArrayList<>();

    @Override//通道就绪
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.add(incoming);
        System.out.println("[Server]:"+incoming.remoteAddress().toString().substring(1)+"在线");
    }

    @Override//通道未就绪
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.remove(incoming);
        System.out.println("[Server]:"+incoming.remoteAddress().toString().substring(1)+"掉线");
    }

    @Override//读取数据
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel incoming = channelHandlerContext.channel();
        for (Channel channel : channels) {
            if(channel != incoming){//排除当前通道
                channel.writeAndFlush("["+incoming.remoteAddress().toString().substring(1)+"]说:"+s+"\n");
            }
        }
    }

    @Override//发生异常
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[Server]:"+channel.remoteAddress().toString().substring(1)+"异常");
        ctx.close();
    }
}
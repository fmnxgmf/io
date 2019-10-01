package cn.fmnx.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/1 14:13
 * @version: 客户端的业务处理类
 * @modified By:
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    //通道就绪事件
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("clietn:"+ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("老板还钱把", CharsetUtil.UTF_8));
    }

    @Override
    //通道读取数据事件
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("服务器端发来消息:"+in.toString(CharsetUtil.UTF_8));
    }

    @Override
    //数据读取完毕事件
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    //异常发生事件
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       ctx.close();
    }
}

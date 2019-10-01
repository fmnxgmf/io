package cn.fmnx.netty.server;

import cn.fmnx.protobuf.BookMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/1 13:43
 * @version: 服务器端业务处理类
 * @modified By:
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    //读取数据事件
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server:"+ctx);
       // ByteBuf buffer = (ByteBuf) msg;
        BookMessage.Book buffer = (BookMessage.Book)msg;
        //System.out.println("客户端发来的消息:"+buffer.toString(CharsetUtil.UTF_8));
        System.out.println("客户端发来的消息:"+buffer.getId()+"*****"+buffer.getName());
    }

    @Override
    //数据读取完毕事件
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("就是没有钱",CharsetUtil.UTF_8));
    }

    @Override
    //异常发生事件
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

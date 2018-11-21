package com.enshub.ch3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.logging.Logger;

public class TimeClientHandler  extends ChannelInboundHandlerAdapter {
    private static final Logger logger=Logger.getLogger("client");
    private final ByteBuf firstMessage;

    public TimeClientHandler() {
        System.out.println("start contruct");
        byte[] req="QUERY TIME ORDER".getBytes();
        firstMessage=Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("before  send");
        ctx.writeAndFlush(firstMessage);
        System.out.println("after  send");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("before  read");
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String body = new String(bytes, "UTF-8");
        System.out.println("now is :"+body);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("unexcepted exception:"+cause.getMessage());
        ctx.close();
    }
}

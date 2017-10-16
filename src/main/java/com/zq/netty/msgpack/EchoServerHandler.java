package com.zq.netty.msgpack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;

public class EchoServerHandler extends ChannelInboundHandlerAdapter{

    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        List<Userinfo> s = (List<Userinfo>) msg;
        System.out.println("Server receive the msgpack message: ");
        System.out.println(s);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

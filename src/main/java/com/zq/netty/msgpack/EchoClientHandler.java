package com.zq.netty.msgpack;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends
        ChannelInboundHandlerAdapter {

    private int counter;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Userinfo userinfo = new Userinfo();
        userinfo.setName("ABCDEF");
        userinfo.setAge(28);
        ctx.writeAndFlush(userinfo);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client receive the msgpack message: " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
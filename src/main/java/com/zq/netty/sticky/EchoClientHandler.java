package com.zq.netty.sticky;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import java.util.stream.IntStream;

@ChannelHandler.Sharable
public class EchoClientHandler extends
        ChannelInboundHandlerAdapter {

    private byte[] messages = "Netty rocks!".getBytes();
    private int counter;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        IntStream.range(0,100).forEach(i -> {
            ctx.writeAndFlush(Unpooled.copiedBuffer("nihao\n".getBytes()));
        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("Response from server: " + body + "; the counter is " + ++counter);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
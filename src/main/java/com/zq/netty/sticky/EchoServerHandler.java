package com.zq.netty.sticky;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter{

    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String body = (String) msg;

        //因为使用了LineBasedFrameDecoder，必须在行尾加\n，否则client端将打印不出来结果
        body = body + "\n";
        System.out.println("Server received: " + body + "; the counter is " + ++count);
        ctx.writeAndFlush(Unpooled.copiedBuffer(body.getBytes()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

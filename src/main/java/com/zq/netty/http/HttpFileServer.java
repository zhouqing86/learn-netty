package com.zq.netty.http;

import com.zq.netty.echo.EchoServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;

public class HttpFileServer {
    private static final String URL="/src/main/java";

    private String url;
    private int port;

    public HttpFileServer(String url, int port) {
        this.url = url;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("http-decoder", new HttpRequestDecoder());
                            pipeline.addLast("http-aggregator", new HttpObjectAggregator(65536));
                            pipeline.addLast("http-encoder", new HttpResponseEncoder());
                            pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                            pipeline.addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();
            System.out.println("HTTP服务器已经启动，启动在http://127.0.0.1:"+port+url);
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8888;
        String url = URL;
        if(args.length > 1) {
            url = args[1];
        }
        new HttpFileServer(url, port ).start();
    }

}

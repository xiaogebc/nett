package com.shadowfax.netty.thirdSocketExample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by gechao on 2017/5/26.
 */
public class MyChatClient {

    public static void main(String[] args) throws Exception {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).
                    handler(new MyChatClientInitializer());

            Channel channel = bootstrap.connect("localhost", 8899).sync().channel();

//            channelFuture.channel().closeFuture().sync();

            //不断的读取控制台输入的内容
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            for( ; ; ) {

                channel.writeAndFlush(br.readLine() + "\n");

            }

        } finally {

            eventLoopGroup.shutdownGracefully();

        }

    }

}

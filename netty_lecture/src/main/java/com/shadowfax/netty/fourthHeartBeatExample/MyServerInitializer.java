package com.shadowfax.netty.fourthHeartBeatExample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by gechao on 2017/5/27.
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        /**
         * 空闲状态检测处理器
         *     IdleStateHandler(读空闲、 写空闲、 读写空闲、 空闲时间单位)
         * 采用责任链模式
         */
        pipeline.addLast(new IdleStateHandler(5, 7, 3, TimeUnit.SECONDS));

        pipeline.addLast(new MyServerHandler());

    }

}

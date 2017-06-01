package com.shadowfax.netty.secondSocketExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * Created by gechao on 2017/5/24.
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 服务器端向客户端发送消息时， channelRead0被触发调用。
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println(ctx.channel().remoteAddress());

        System.out.println("client output : " + msg);

        ctx.writeAndFlush("from client : " + LocalDateTime.now());

    }

    /**
     * 回调函数
     *
     * 让客户端向服务端发送数据, 否则一直等待
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("come from client request!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();

        ctx.close();

    }
}

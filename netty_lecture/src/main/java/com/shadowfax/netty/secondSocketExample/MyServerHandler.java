package com.shadowfax.netty.secondSocketExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * 处理器
 * Created by gechao on 2017/5/24.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 读取客户端发送的请求，并且向客户端返回响应的方法。
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        //打印远程地址
        System.out.println(ctx.channel().remoteAddress() + "," + msg);

        /**
         * 设置返回客户端信息
         */
        ctx.channel().writeAndFlush("from server : " + UUID.randomUUID());

    }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);

        cause.printStackTrace();

        ctx.close();

    }
}

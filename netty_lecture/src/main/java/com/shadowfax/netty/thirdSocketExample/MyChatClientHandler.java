package com.shadowfax.netty.thirdSocketExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by gechao on 2017/5/26.
 */
public class MyChatClientHandler extends SimpleChannelInboundHandler<String>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        //打印从服务端返回的数据
        System.out.println(msg);

    }
}

package com.shadowfax.netty.thirdSocketExample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 传递的信息为String <br>
 *     A B C 与服务端建立连接。
 *     A已连接， B与服务器连接时， 服务器打印B已上线， 同时告诉A B已上线。
 *     A下线， 服务器打印A下线， 通知已连接客户端A已下线。
 * Created by gechao on 2017/5/26.
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 用于保存与服务器端建立连接的channel对象
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 处理交互数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        //向服务器端发送消息的连接, 即 发送消息本身的对象
        Channel channel = ctx.channel();

        /**
         * channelGroup中挑出自己来
         */
        channelGroup.forEach(ch -> {

            if(channel != ch) {

                //不是发消息的客户端
                ch.writeAndFlush(channel.remoteAddress() + "发送的消息：" + msg + "\n");

            } else {

                //自己
                ch.writeAndFlush(" [自己]" + msg + "\n");

            }

        });

    }

    /**
     * 连接建立
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        //连接对象
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[服务器端] -" + channel.remoteAddress() + "加入\n");

        channelGroup.add(channel);

    }

    /**
     * 连接断开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + "离开\n");

//        channel.close(); // 自动被调用    channelGroup.remove() 自动被调用

        System.out.println(channelGroup.size());
    }

    /**
     * 激活连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();

        System.out.println(channel.remoteAddress() + "上线");
        
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();

        System.out.println(channel.remoteAddress() + "下线");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();

        ctx.close();

    }
}

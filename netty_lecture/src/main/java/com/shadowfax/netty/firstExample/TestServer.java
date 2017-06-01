package com.shadowfax.netty.firstExample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty http服务器
 *
 * Created by gechao on 2017/5/21.
 */
public class TestServer {

    public static void main(String[] args) throws Exception{

        /**
         * 定义事件循环组（线程组 ）
         * bossGroup 和 workGroup 都是死循环，永远不会退出
         */
        //只负责链接
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        //负责链接的处理
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {

            //用于启动服务端, ServerBootstrap为netty简化服务端启动提供的class
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            //childHandler 添加处理器
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).
                    childHandler(new TestServerInitializer());
            //handler() 针对bossGroup，  childHandler针对workGroup。

            //将服务绑定在8899端口。
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();

            //定义关闭的监听
            channelFuture.channel().closeFuture().sync();

        } finally {

            //优雅关闭
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();

        }



    }

}

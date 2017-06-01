package com.shadowfax.netty.firstExample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 *  服务端的初始化器， channel 被注册好之后，接着被创建执行。
 * Created by gechao on 2017/5/21.
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        //添加处理器
        /**
         * HttpServerCodec 用于完成服务器端的编解码
         * HttpResponseEncoder 编码
         * HttpRequestDecoder 解码
         */
        pipeline.addLast("httpServerCodec", new HttpServerCodec());//DefaultHttpRequest

        pipeline.addLast("testHttpServerHandler", new TestHttpServerHandler());

    }
}

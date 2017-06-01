package com.shadowfax.netty.fifthWebsocketExample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by gechao on 2017/5/29.
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        /**
         * HttpServerCodec http编解码器
         */
        pipeline.addLast(new HttpServerCodec());

        /**
         * ChunkedWriteHandler 分块的方式写处理器
         */
        pipeline.addLast(new ChunkedWriteHandler());

        /**
         * Aggregator 聚合
         *
         * HttpObjectAggregator 将分段的10端聚合到一起，行成一个完整的http请求或http 响应。
         */
        pipeline.addLast(new HttpObjectAggregator(8192));

        /**
         * WebSocketServerProtocolHandler : netty专门提供处理websocket的类
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new TestWebSocketFrameHandler());

    }
    
}

package com.shadowfax.netty.secondSocketExample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * 服务端的初始化器
 *
 * Created by gechao on 2017/5/24.
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * initChannel: 客户端一旦与服务器端链接后， 该对象就会被创建。 且initChannel就会得到调用。
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        //Netty提供处理器
        /**
         * 解码器
         */
        pipeline.addLast("lengthFieldBasedFrameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));

        /**
         * 编码器，prepender消息长度
         */
        pipeline.addLast("lengthFieldPrepender", new LengthFieldPrepender(4));

        /**
         * 字符串编解码
         */
        pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));

        pipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));

        //自己提供处理器
        pipeline.addLast(new MyServerHandler());

    }
}

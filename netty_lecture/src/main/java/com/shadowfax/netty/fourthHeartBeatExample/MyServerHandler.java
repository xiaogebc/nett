package com.shadowfax.netty.fourthHeartBeatExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 自定义的处理器， 功能： 检测IdleStateHandler事件， 对响应状态处理
 * Created by gechao on 2017/5/27.
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     *
     * @param ctx  上下文对象
     * @param evt  事件对象
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if(evt instanceof IdleStateEvent) { //是一个空闲状态事件

            IdleStateEvent event = (IdleStateEvent) evt;

            String eventType = null;

            switch (event.state()) {

                case READER_IDLE :

                    eventType = "读空闲";

                    break;

                case WRITER_IDLE:

                    eventType = "写空闲";

                    break;

                case  ALL_IDLE:

                    eventType = "读写空闲";

                    break;
            }

            System.out.println(ctx.channel().remoteAddress() + "超时事件：" + eventType);

            //链接关闭
            ctx.channel().close();

        }

    }
}

package com.shadowfax.netty.firstExample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.nio.charset.Charset;

/**
 * HttpObject 为传递的信息
 * Created by gechao on 2017/5/21.
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端发送的请求，并且向客户端返回响应的方法。
     *
     *
     * @param ctx  ChannelHandlerContext 表示上下文获取相关信息。
     * @param msg  HttpObject 客户端发送过来的请求对象。
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println(msg.getClass());

        //获取远程访问地址
        System.out.println(ctx.channel().remoteAddress());

        if(msg instanceof HttpRequest) {

            HttpRequest httpRequest = (HttpRequest) msg;

            System.out.println("请求方法名为：" + httpRequest.method().name());

            URI uri = new URI(httpRequest.uri());

            if("/favicon.ico".equals(uri.getPath())) {

                System.out.println("请求favicon.ico");

                return;

            }

            //构造返回内容
            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);

            //构造Http响应, netty 提供支撑响应的对象FullHttpResponse。
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, content);

            //设置http头信息
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");//响应内容类型
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());//响应内容长度

            //返回客户端
            ctx.writeAndFlush(response);

            ctx.channel().close();

        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("channelActive");

        super.channelActive(ctx);

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

        System.out.println("channelRegistered");

        super.channelRegistered(ctx);

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        System.out.println("handlerAdded");

        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("channelInactive");

        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

        System.out.println("channelUnregistered");

        super.channelUnregistered(ctx);
    }
}

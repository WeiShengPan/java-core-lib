package com.pws.netty.fullhttp;

import com.pws.javafeatures.util.PrintUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

/**
 * @author panws
 * @since 2018-04-19
 */
public class FullHttpClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PrintUtil.printSep();
        PrintUtil.println("Client channel read start...");
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            HttpHeaders headers = response.headers();
            ByteBuf receivedContent = response.content();
            byte[] receivedByte = new byte[receivedContent.readableBytes()];
            receivedContent.readBytes(receivedByte);
            String received = new String(receivedByte, StandardCharsets.UTF_8);
            PrintUtil.println("Read: %s", received);
            PrintUtil.println("Read: %s", msg);
        } else if (msg instanceof ByteBuf) {
            ByteBuf response = (ByteBuf) msg;
            byte[] receivedByte = new byte[response.readableBytes()];
            response.readBytes(receivedByte);
            String received = new String(receivedByte, StandardCharsets.UTF_8);
            PrintUtil.println("Read: %s", received);
            PrintUtil.println("Read: %s", msg);
        } else {
            PrintUtil.err("Client channel read ERROR!");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        PrintUtil.err("Exception caught: %s", cause);
        ctx.close();
    }

    /**
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.println("Client channel active!");

        String send = "Hello World, this is client!";
        ByteBuf byteBuf = Unpooled.copiedBuffer(send.getBytes());

        DefaultFullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1,
                HttpMethod.GET,
                "http://127.0.0.1:8899",
                byteBuf);
        request.headers().set(HttpHeaderNames.HOST, "127.0.0.1");
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

        ctx.writeAndFlush(request);

        PrintUtil.println("Send: %s", send);
        PrintUtil.println("Send: %s", request);
    }
}

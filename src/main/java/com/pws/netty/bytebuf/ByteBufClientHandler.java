package com.pws.netty.bytebuf;

import com.pws.javafeatures.util.PrintUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author panws
 * @since 2018-04-19
 */
public class ByteBufClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        PrintUtil.println("Client channel read start...");

        byte[] receivedByte = new byte[msg.readableBytes()];
        msg.readBytes(receivedByte);
        String received = new String(receivedByte, StandardCharsets.UTF_8);
        PrintUtil.println("Read: %s", received);
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
        ctx.writeAndFlush(byteBuf);
        PrintUtil.println("Send: %s", send);
    }
}

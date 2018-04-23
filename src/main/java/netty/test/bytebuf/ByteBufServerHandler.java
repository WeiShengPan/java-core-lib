package netty.test.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafeatures.util.PrintUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author panws
 * @since 2018-04-19
 */
public class ByteBufServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        PrintUtil.println("Server channel read start...");

        byte[] receivedByte = new byte[msg.readableBytes()];
        msg.readBytes(receivedByte);
        String received = new String(receivedByte, StandardCharsets.UTF_8);
        PrintUtil.println("Read: %s", received);

        String send = "This is Server!";
        ByteBuf sendByte = Unpooled.copiedBuffer(send.getBytes());
        ctx.write(sendByte);
        PrintUtil.println("Send: %s", send);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.println("Server channel read complete!");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        PrintUtil.err("Exception caught: %s", cause);
        ctx.close();
    }
}

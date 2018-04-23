package netty.test.fullhttp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import javafeatures.util.PrintUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author panws
 * @since 2018-04-20
 */
public class FullHttpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PrintUtil.printSep();
        PrintUtil.println("Server channel read start...");
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            HttpMethod method = request.method();
            ByteBuf receivedContent = request.content();
            byte[] receivedByte = new byte[receivedContent.readableBytes()];
            receivedContent.readBytes(receivedByte);
            String received = new String(receivedByte, StandardCharsets.UTF_8);
            PrintUtil.println("Read: [%s] %s", method.toString(), received);
            PrintUtil.println("Read: %s", msg);

            PrintUtil.printSep();
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer("This is server!".getBytes()
                    ));

            HttpHeaders headers = response.headers();
            headers.add(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
            headers.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            headers.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            ctx.writeAndFlush(response);
            PrintUtil.println("Send: %s", response);
        } else {
            PrintUtil.err("Server channel read ERROR!");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        PrintUtil.err("Exception caught: %s", cause);
        ctx.close();
    }
}

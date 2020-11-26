package com.pws.netty.fullhttp;

import com.pws.javafeatures.util.PrintUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

/**
 * @author panws
 * @since 2018-04-19
 */
public class FullHttpClient {

    private static final FullHttpClient INSTANCE = new FullHttpClient();

    private FullHttpClient() {
    }

    public static FullHttpClient getInstance() {
        return INSTANCE;
    }

    private void connect(String host, int port) {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                //[inbound] HttpResponseDecoder，将ByteBuf解析成HttpResponse
                                //!!    注意这里是HttpResponseDecoder，client接收的是HttpResponse     !!
                                .addLast("Decoder", new HttpResponseDecoder())

                                //[outbound]    HttpRequestEncoder，将HttpRequest装载成ByteBuf
                                //!!    注意这里是HttpRequestEncoder，client发送的是HttpRequest       !!
                                //!!!   最后一个outbound必须在最后一个inbound之前        !!!
                                .addLast("Encoder", new HttpRequestEncoder())

                                //消息聚合器，参数为消息合并的数据大小，保证数据由同一个channel处理，大小超过限制的数据会由多个channel处理，对业务开发不方便
                                .addLast("Aggregator", new HttpObjectAggregator(512 * 1024))

								//[inbound]
                                .addLast("FullHttpClientHandler", new FullHttpClientHandler());
                    }
                })
                .option(ChannelOption.SO_KEEPALIVE, true);

        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            PrintUtil.err("Error occurs when client start: %s", e);
        }
    }

    public static void main(String[] args) {
        FullHttpClient.getInstance().connect("127.0.0.1", FullHttpServer.PORT);
    }
}

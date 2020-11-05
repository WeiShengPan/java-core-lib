package netty.fullhttp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import javafeatures.util.PrintUtil;

/**
 * 在使用Handler的过程中，需要注意：
 * 1、ChannelInboundHandler之间的传递，通过调用 ctx.fireChannelRead(msg) 实现；调用ctx.write(msg) 将传递到ChannelOutboundHandler。
 * 2、ctx.write()方法执行后，需要调用flush()方法才能令它立即执行。
 * 3、流水线pipeline中outbound不能放在最后，否则不生效，最后一个outbound必须在最后一个inbound之前
 * 4、Handler的消费处理放在最后一个处理。
 *
 * @author panws
 * @since 2018-04-19
 */
public class FullHttpServer {

    static final int PORT = 9999;

    private static final FullHttpServer INSTANCE = new FullHttpServer();

    private FullHttpServer() {
    }

    public static FullHttpServer getInstance() {
        return INSTANCE;
    }

    public void start(int port) {

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        PrintUtil.println("Init channel: %s", socketChannel);
                        socketChannel.pipeline()
                                //[inbound] HttpRequestDecoder用于解码request，将ByteBuf解析成HttpRequest
                                //!!    注意这里是HttpRequestDecoder，server接收的是HttpRequest       !!
                                .addLast("Decoder", new HttpRequestDecoder())

                                //[outbound]    HttpResponseEncoder用于编码response，将HttpResponse装载成ByteBuf
                                //!!    注意这里是HttpResponseEncoder，server发送的是HttpResponse     !!
                                //!!!   最后一个outbound必须在最后一个inbound之前        !!!
                                .addLast("Encoder", new HttpResponseEncoder())

                                //消息聚合器，参数为消息合并的数据大小，保证数据由同一个channel处理，大小超过限制的数据会由多个channel处理，对业务开发不方便
                                .addLast("Aggregator", new HttpObjectAggregator(512 * 1024))

                                //[inbound]     自定义处理器
                                .addLast("FullHttpServerHandler", new FullHttpServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

        try {
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            PrintUtil.err("Error occurs when server start: %s", e);
        }
    }

    public static void main(String[] args) {
        FullHttpServer.getInstance().start(PORT);
    }
}

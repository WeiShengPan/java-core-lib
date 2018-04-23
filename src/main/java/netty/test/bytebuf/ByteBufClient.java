package netty.test.bytebuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import javafeatures.util.PrintUtil;

/**
 * @author panws
 * @since 2018-04-19
 */
public class ByteBufClient {

    private static final ByteBufClient INSTANCE = new ByteBufClient();

    private ByteBufClient() {
    }

    public static ByteBufClient getInstance() {
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
                        ch.pipeline().addLast("ByteBufClientHandler", new ByteBufClientHandler());
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
        ByteBufClient.getInstance().connect("127.0.0.1", ByteBufServer.PORT);
    }
}

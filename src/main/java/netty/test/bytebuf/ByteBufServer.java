package netty.test.bytebuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import javafeatures.util.PrintUtil;

/**
 * @author panws
 * @since 2018-04-19
 */
public class ByteBufServer {

    static final int PORT = 9999;

    private static final ByteBufServer INSTANCE = new ByteBufServer();

    private ByteBufServer() {
    }

    public static ByteBufServer getInstance() {
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
                                //[inbound]     自定义处理器
                                .addLast("ByteBufServerHandler", new ByteBufServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

        try {
            serverBootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            PrintUtil.err("Error occurs when server start: %s", e);
        }
    }

    public static void main(String[] args) {
        ByteBufServer.getInstance().start(PORT);
    }
}

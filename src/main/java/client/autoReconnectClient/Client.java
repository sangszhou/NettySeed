package client.autoReconnectClient;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by xinszhou on 5/20/16.
 */
public class Client {

    private EventLoopGroup loop = new NioEventLoopGroup();

    public static void main(String args[]) {
        new Client().run();
    }

    public Bootstrap createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) {

        assert(bootstrap != null);

        final MyInboundHandler inboundHandler = new MyInboundHandler(this);

        bootstrap.group(eventLoop);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(inboundHandler);
            }
        });

        bootstrap.remoteAddress("localhost", 8087);
        bootstrap.connect().addListener(new ConnectionListener(this));

        return bootstrap;

    }

    public void run() {
        createBootstrap(new Bootstrap(), loop);
    }
}

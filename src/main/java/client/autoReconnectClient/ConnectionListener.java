package client.autoReconnectClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by xinszhou on 5/20/16.
 */
public class ConnectionListener implements ChannelFutureListener {

    Logger log = LoggerFactory.getLogger(getClass());

    private Client client;

    public ConnectionListener(Client client) {
        this.client = client;
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        if(! future.isSuccess()) {
            log.info("channel future is not succes, reconnect bootstrap");

            final EventLoop loop = future.channel().eventLoop();

            loop.schedule(new Runnable() {
                public void run() {
                    client.createBootstrap(new Bootstrap(), loop);
                }
            }, 3L, TimeUnit.SECONDS);
        }
    }

}

package client.autoReconnectClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.util.concurrent.TimeUnit;

/**
 * Created by xinszhou on 5/20/16.
 */
public class MyInboundHandler extends SimpleChannelInboundHandler {

    Logger log = LoggerFactory.getLogger(getClass());

    public Client client;

    public MyInboundHandler(Client client) {
        this.client = client;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        log.info("channel inactive from server, try to reconnect channel");

        //why eventLoop is attached to channel?
        final EventLoop eventLoop = ctx.channel().eventLoop();

        eventLoop.schedule(new Runnable() {
            public void run() {
                client.createBootstrap(new Bootstrap(), eventLoop);
            }
        }, 3L, TimeUnit.SECONDS);
    }

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //read message

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.fireExceptionCaught(cause);
    }


}

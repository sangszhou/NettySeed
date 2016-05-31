package client.SpringLikeHandler;

import client.autoReconnectClient.ConnectionListener;
import client.util.GlobalConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.config.Task;
import util.TaskPromise;
import util.impl.DefaultTaskPromise;


/**
 * Created by xinszhou on 5/27/16.
 */
public class NettyHttpExecutor {
    Logger log = LoggerFactory.getLogger(getClass());

    final ChannelFuture channelFuture;
    Bootstrap bootstrap;

    public NettyHttpExecutor(ChannelFuture channelFuture, Bootstrap bootstrap) {
        this.channelFuture = channelFuture;
        this.bootstrap = bootstrap;
    }

    public NettyHttpExecutor(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    /**
     * not thread safe
     * @param httpRequest
     * @return
     */
    public TaskPromise executeInternal(HttpRequest httpRequest) {
        final TaskPromise promise = new DefaultTaskPromise();

        if(bootstrap == null) {

            log.info("new created promise hashcode is " + promise.hashCode());

            Channel channel = channelFuture.channel();
            channel.pipeline().get(ResponseHandler.class).setResponseHandler(promise);

            channel.writeAndFlush(httpRequest).addListener((ChannelFutureListener) future -> {
                if(future.isSuccess()) {
                    log.info("write success");
                }
            });
        } else {
            log.info("create bootstrap per request");

            ChannelFutureListener listener = new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()) {
                        Channel channel = future.channel();
                        channel.pipeline().get(ResponseHandler.class).setResponseHandler(promise);
                        channel.writeAndFlush(httpRequest);
                    }
                }
            };
            bootstrap.connect(GlobalConfig.host, GlobalConfig.port).addListener(listener);

        }

        return promise;
    }

}

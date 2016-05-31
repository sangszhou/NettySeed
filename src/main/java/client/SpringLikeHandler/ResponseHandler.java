package client.SpringLikeHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.TaskPromise;

/**
 * Created by xinszhou on 5/27/16.
 */
public class ResponseHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    Logger log = LoggerFactory.getLogger(getClass());

    private TaskPromise promise;

    public void setResponseHandler(TaskPromise promise) {
        this.promise = promise;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {
        log.info("channel read0 returned");
        promise.setSuccess(new NettyHttpResponse(ctx, msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
        log.info("exception caught in response handler");
        this.promise.setFailure(cause);
    }

}

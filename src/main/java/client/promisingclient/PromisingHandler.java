package client.promisingclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * Created by xinszhou on 5/25/16.
 */
public class PromisingHandler extends SimpleChannelInboundHandler<HttpObject> {

    Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.error("channel active, client connected to server");
    }

    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

//        System.err.println("START OF CONTENT {");

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;

//            System.err.print(content.content().toString(CharsetUtil.UTF_8));
//            System.err.flush();

            if (content instanceof HttpContent) {
                sendFullResponse(ctx, content);
            } else {
                log.error("content is not DefaultFullHttpResponse");
            }
        }

    }

    void sendFullResponse(ChannelHandlerContext ctx, HttpContent msg) {
        log.info("send full response, hook http response to promise");

        RequestContext requestContext = ctx.channel().attr(HttpClient.KEY).get();

        //told promise that result msg has been received
        requestContext.promise.setSuccess(referencedObjectCpy(msg));
    }

    private String referencedObjectCpy(HttpContent msg) {
//        FullHttpResponse httpContent = msg.duplicate();

        byte[] bytes = new byte[msg.content().readableBytes()];
        int readIndex = msg.content().readerIndex();
        msg.content().getBytes(readIndex, bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }


}

package client.springClient;

import client.httpClient.HttpRequestBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.io.IOException;

/**
 * Created by xinszhou on 5/31/16.
 */
public class Netty4ClientHttpRequest {

    private final Bootstrap bootstrap;
    private final String host;
    private final int port;

    public Netty4ClientHttpRequest(Bootstrap bootstrap, String host, int port) {
        this.bootstrap = bootstrap;
        this.host = host;
        this.port = port;
    }

    protected ListenableFuture<ClientHttpResponse> executeInternal(HttpRequest request) throws IOException {
        final SettableListenableFuture<ClientHttpResponse> responseFuture =
                new SettableListenableFuture<ClientHttpResponse>();

        ChannelFutureListener connectionListener = new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    Channel channel = future.channel();
                    channel.pipeline().addLast(new RequestExecuteHandler(responseFuture));
                    channel.writeAndFlush(request);
                }
                else {
                    responseFuture.setException(future.cause());
                }
            }
        };

        this.bootstrap.connect(host, port).addListener(connectionListener);

        return responseFuture;
    }

    private static class RequestExecuteHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

        private final SettableListenableFuture<ClientHttpResponse> responseFuture;

        public RequestExecuteHandler(SettableListenableFuture<ClientHttpResponse> responseFuture) {

            this.responseFuture = responseFuture;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext context, FullHttpResponse response) throws Exception {
            System.out.println("receive message from server");
            System.out.println(response.getStatus().code());
            System.out.println(response.content());

            this.responseFuture.set(new Netty4ClientHttpResponse(context, response));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
            this.responseFuture.setException(cause);
        }
    }



}

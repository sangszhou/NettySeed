package client.httpClient;

import client.util.CommonClientBootStrap;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;

/**
 * Created by xinszhou on 5/20/16.
 */
public class HttpClientTest {

    public static void main(String args[]) {

        HttpRequest httpRequest = HttpRequestBuilder.createGetRequest();

        ChannelInboundHandler oneShotHandler = new OneshotHandler();
        Bootstrap bootstrap = CommonClientBootStrap.createBootStrap(oneShotHandler);
        try {
            Channel channel = bootstrap.connect("icam-dev-soa-01", 2015).sync().channel();

            //send http request
            channel.writeAndFlush(httpRequest);

            // Wait for the server to close the connection.
            channel.closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}

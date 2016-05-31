package client.SpringLikeHandler;

import client.httpClient.HttpRequestBuilder;
import client.promisingclient.PromisingHandler;
import client.util.CommonClientBootStrap;
import client.util.GlobalConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import util.TaskPromise;
import util.impl.DefaultTaskPromise;

/**
 * Created by xinszhou on 5/27/16.
 */
public class NettyHttpClient {

    NettyHttpExecutor executor;

    Bootstrap bootstrap;
    ChannelFuture channelFuture;
    HttpRequest httpRequest;

    public NettyHttpClient() {
        bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                p.addLast(new HttpClientCodec());
                p.addLast(new HttpContentDecompressor());
                p.addLast(new HttpObjectAggregator(512 * 1024));
                p.addLast(new ResponseHandler());
            }
        });

        httpRequest = HttpRequestBuilder.createGetRequest();

        try {
            channelFuture = bootstrap.connect(GlobalConfig.host, GlobalConfig.port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor = new NettyHttpExecutor(channelFuture);
    }

    public NettyHttpClient(Bootstrap bootstrap, HttpRequest httpRequest) {
        this.bootstrap = bootstrap;
        this.httpRequest = httpRequest;
        ChannelFuture channelFuture = bootstrap.connect(GlobalConfig.host, GlobalConfig.port);
        executor = new NettyHttpExecutor(channelFuture, bootstrap);
    }


    public TaskPromise sendRequest() {
        return executor.executeInternal(httpRequest);
    }

    public TaskPromise sendRequest(HttpRequest request) {
        return executor.executeInternal(request);
    }


}

package client.promisingclient;

import client.httpClient.HttpRequestBuilder;
import client.util.CommonClientBootStrap;
import client.util.GlobalConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.TaskFuture;

/**
 * Created by xinszhou on 5/25/16.
 */
public class HttpClient {

    Logger log = LoggerFactory.getLogger(getClass());

    Bootstrap bootstrap;
    HttpRequest httpRequest;

    static final AttributeKey<RequestContext> KEY = AttributeKey.<RequestContext>valueOf("RequestContext");

    public HttpClient() {
        this(CommonClientBootStrap.createBootStrap(new PromisingHandler()), HttpRequestBuilder.createGetRequest());
    }

    public HttpClient(Bootstrap bootstrap, HttpRequest httpRequest) {
        this.bootstrap = bootstrap;
        this.httpRequest = httpRequest;
    }

    public TaskFuture execute() {


        RequestContext requestContext = new RequestContext();

        try {
            Channel channel = bootstrap.connect(GlobalConfig.host, GlobalConfig.port).sync().channel();

            channel.attr(KEY).set(requestContext);

            // send http request
            channel.writeAndFlush(httpRequest);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return requestContext.promise.getFuture();
    }


}

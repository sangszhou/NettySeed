package client.springClient;

import client.httpClient.HttpRequestBuilder;
import client.util.CommonClientBootStrap;
import client.util.GlobalConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by xinszhou on 5/31/16.
 */
public class NettyClientTests {
    public static void main(String[] args) {

        Bootstrap bootstrap = CommonClientBootStrap.createBootStrap(null);

        String host = GlobalConfig.host;
        int port = GlobalConfig.port;

        Netty4ClientHttpRequest requestExe = new Netty4ClientHttpRequest(bootstrap, host, port);

        try {

            for(int i = 0; i < 10; i ++ ) {
                ClientHttpResponse response = requestExe.executeInternal(HttpRequestBuilder.createGetRequest()).get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}

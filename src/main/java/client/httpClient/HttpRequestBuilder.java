package client.httpClient;

import io.netty.handler.codec.http.*;

/**
 * Created by xinszhou on 5/20/16.
 */
public class HttpRequestBuilder {
    public static FullHttpRequest createGetRequest() {
        FullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, "/cluster");

        request.headers().set(HttpHeaders.Names.HOST, "icam-dev-soa-01");
        request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        request.headers().add(HttpHeaders.Names.CONTENT_TYPE, "application/json");

        return request;
    }

}

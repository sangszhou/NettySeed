package client.springClient;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by xinszhou on 5/31/16.
 */
public class Netty4ClientHttpResponse implements ClientHttpResponse {

    private final ChannelHandlerContext context;

    private final FullHttpResponse nettyResponse;

    private final ByteBufInputStream body;

    private volatile HttpHeaders headers;


    public Netty4ClientHttpResponse(ChannelHandlerContext context, FullHttpResponse nettyResponse) {
        Assert.notNull(context, "ChannelHandlerContext must not be null");
        Assert.notNull(nettyResponse, "FullHttpResponse must not be null");
        this.context = context;
        this.nettyResponse = nettyResponse;
        this.body = new ByteBufInputStream(this.nettyResponse.content());
        this.nettyResponse.retain();
    }


    @Override
    public HttpStatus getStatusCode() throws IOException {
        return null;
    }

    public int getRawStatusCode() throws IOException {
        return this.nettyResponse.getStatus().code();
    }


    public String getStatusText() throws IOException {
        return this.nettyResponse.getStatus().reasonPhrase();
    }


    public HttpHeaders getHeaders() {
        if (this.headers == null) {
            HttpHeaders headers = new HttpHeaders();
            for (Map.Entry<String, String> entry : this.nettyResponse.headers()) {
                headers.add(entry.getKey(), entry.getValue());
            }
            this.headers = headers;
        }
        return this.headers;
    }


    public InputStream getBody() throws IOException {
        return this.body;
    }


    public void close() {
        this.nettyResponse.release();
        this.context.close();
    }
}

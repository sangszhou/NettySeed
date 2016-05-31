package client.SpringLikeHandler;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by xinszhou on 5/27/16.
 */
public class NettyHttpResponse {
    private final ChannelHandlerContext context;
    private final FullHttpResponse nettyResponse;
    private final ByteBufInputStream body;
    private HttpHeaders headers;

    public NettyHttpResponse(ChannelHandlerContext context, FullHttpResponse nettyResponse) {
        this.context = context;
        this.nettyResponse = nettyResponse;

        this.body = new ByteBufInputStream(this.nettyResponse.content());
        this.nettyResponse.retain();
    }

    public int getRawStatusCode() throws IOException {
        return this.nettyResponse.getStatus().code();
    }


    public String getStatusText() throws IOException {
        return this.nettyResponse.getStatus().reasonPhrase();
    }

    public org.springframework.http.HttpHeaders getHeaders() {
        if (this.headers == null) {
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
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

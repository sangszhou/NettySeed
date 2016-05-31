package restTemplate;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.http.*;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.concurrent.ExecutionException;

/**
 * Created by xinszhou on 5/27/16.
 */
public class Netty4RestTemplate {
    public static void main(String args[]) {

        Netty4ClientHttpRequestFactory factory = new Netty4ClientHttpRequestFactory();

        AsyncRestTemplate asycTemp = new AsyncRestTemplate(factory);

        String url = "http://icam-dev-mysql1:9200";

        Class<Object> responseType = Object.class;

        ListenableFuture<ResponseEntity<Object>> future = asycTemp.getForEntity(url, responseType);

        try {
            //waits for the result
            ResponseEntity<Object> entity = future.get();

            System.out.println(entity.getBody());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

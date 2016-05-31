package client.springClient;

import client.SpringLikeHandler.NettyHttpClient;
import org.springframework.http.*;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

/**
 * Created by xinszhou on 5/31/16.
 */
public class HttpClientTests {
    public static void main(String[] args) {
        Netty4ClientHttpRequestFactory factory = new Netty4ClientHttpRequestFactory();

        AsyncRestTemplate asycTemp = new AsyncRestTemplate();

        String url ="icam-dev-mysql1:9200";
        HttpMethod method = HttpMethod.GET;
        Class<String> responseType = String.class;

//        ListenableFuture<ResponseEntity<String>> future = asycTemp.exchange(url, method, null, responseType);
        ListenableFuture<ResponseEntity<String>> future = asycTemp.getForEntity(url, responseType);

        try {

            ListenableFuture<ClientHttpResponse> future2 =  factory.createAsyncRequest(new URI(url) ,HttpMethod.GET).executeAsync();

            ClientHttpResponse response = future2.get();

            System.out.println(response.getStatusCode());


            //waits for the result
            ResponseEntity<String> entity = future.get();

            // prints body source code for the given URL
            System.out.println(entity.getStatusCode());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

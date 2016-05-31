package restTemplate;

import org.springframework.http.*;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.concurrent.ExecutionException;

/**
 * Created by xinszhou on 5/27/16.
 */
public class ExchangeMethodExample {

    public static void main(String[] args) {
        AsyncRestTemplate asycTemp = new AsyncRestTemplate();
        String url = "http://google.com";

        HttpMethod method = HttpMethod.GET;
        Class<String> responseType = String.class;
        //create request entity using HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        HttpEntity<String> requestEntity = new HttpEntity<String>("params", headers);

        ListenableFuture<ResponseEntity<String>> future = asycTemp.exchange(url, method, requestEntity, responseType);

        try {
            //waits for the result
            ResponseEntity<String> entity = future.get();
            //prints body source code for the given URL
            System.out.println(entity.getBody());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}

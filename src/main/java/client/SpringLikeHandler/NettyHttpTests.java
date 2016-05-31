package client.SpringLikeHandler;

import client.httpClient.HttpRequestBuilder;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.CharsetUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.util.CharsetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.TaskCallback;
import util.TaskFuture;
import util.TaskPromise;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by xinszhou on 5/27/16.
 */
public class NettyHttpTests {
    public static void main(String args[]) {

        Logger log = LoggerFactory.getLogger(NettyHttpTests.class);

        NettyHttpClient client = new NettyHttpClient();

        List<String> ids = new ArrayList<String>();
        ids.add("icam-8eC6X4-1435533684138");
        ids.add("icam-4FdkSN-1435533684141");
//        ids.add("icam-RVTRol-1435533684185");


//        for(int i = 0; i < ids.size(); i ++) {
//            String id = ids.get(i);
//
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    HttpRequest request = HttpRequestBuilder.createGetRequest("/icam/alarm/"+id);
//                    TaskPromise promise = client.sendRequest(request);
//                    try {
//                        System.out.println(Thread.currentThread());
//                        Object result = promise.get();
//                        InputStream response = ((NettyHttpResponse)result).getBody();
//                        String theString = IOUtils.toString(response, String.valueOf(StandardCharsets.UTF_8));
//                        System.out.println(theString);
//                        System.out.println(Thread.currentThread());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//            thread.start();
//        }


        try {
            for(int i = 0; i < 1000; i ++) {
                TaskPromise promise = client.sendRequest();
                Object result = promise.get();
                System.out.println("result class: " + result.getClass());
                try {
                    InputStream response = ((NettyHttpResponse)result).getBody();
                    String theString = IOUtils.toString(response , String.valueOf(StandardCharsets.UTF_8));
                    System.out.println(theString);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

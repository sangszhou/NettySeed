package client.promisingclient;

import io.netty.handler.codec.http.HttpContent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.TaskFuture;

import java.util.concurrent.ExecutionException;

/**
 * Created by xinszhou on 5/25/16.
 */
public class HttpClientTest {

    public static void main(String args[] ) {


        HttpClient client = new HttpClient();
        TaskFuture future = client.execute();

        while(! future.isSuccess()) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


//        HttpContent content = null;
        try {
            Object content =  future.get();
            System.err.println(content);
//            System.err.println("content ref count: " + content.refCnt());
//            System.err.print(content.content().toString(CharsetUtil.UTF_8));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}

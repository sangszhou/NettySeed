package client.promisingclient;

import util.TaskPromise;
import util.impl.DefaultTaskPromise;

/**
 * Created by xinszhou on 5/25/16.
 */
public class RequestContext {

    TaskPromise promise;

    public RequestContext() {
        this(new DefaultTaskPromise());
    }

    public RequestContext(TaskPromise promise) {
        this.promise = promise;
    }

}

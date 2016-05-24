### What the api gonna looks like
```
jClient.prepareIndex(index, typ, id).setSource(doc.toString).setRefresh(true).execute().get
```

```
  public ListenableActionFuture<Response> execute() {
    PlainListenableActionFuture<Response> future = new PlainListenableActionFuture<>(request.listenerThreaded(), threadPool);
    execute(future);
    return future;
  }


  public void execute(ActionListener<Response> listener) {
      doExecute(listener);
  }
```


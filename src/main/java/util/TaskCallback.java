package util;

public abstract class TaskCallback {

    /**
     * 执行动作
     *
     * @todo 不知道这里把 TaskFuture 当做参数是否合理
     */
    public abstract TaskFuture apply(TaskFuture f);

    public TaskCallback compose(final TaskCallback before) {
        return new TaskCallback() {
            @Override
            public TaskFuture apply(TaskFuture f) {
                return TaskCallback.this.apply(before.apply(f));
            }
        };
    }

    public TaskCallback andThen(final TaskCallback after) {
        return new TaskCallback() {
            @Override
            public TaskFuture apply(TaskFuture f) {
                return after.apply(TaskCallback.this.apply(f));
            }
        };
    }
}

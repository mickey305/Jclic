package com.mickey305.util.cli;

import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.cli.model.ResultCache;
import com.mickey305.util.cli.model.ResultType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public abstract class Receiver<R> {
    private static final int DEF_STREAM = 0;
    private static final int ERR_STREAM = 1;
    private static final int DEFAULT_PROCESS_TIMEOUT = 120; // プロセスの最長実行時間（秒）

    private Set<ResultCache<String>> resultSet;

    public Receiver() {
        this.initResultSet();
    }

    private void addResult(ResultType type, String result) {
        resultSet.add(new ResultCache<>(type, result));
    }

    private void setResultSet(Set<ResultCache<String>> resultSet) {
        this.resultSet = resultSet;
    }

    private void initResultSet() {
        this.setResultSet(new LinkedHashSet<>());
    }

    public Set<ResultCache<String>> getResultSet() {
        return resultSet;
    }

    protected void createResultSet(Process process) throws InterruptedException {
        if (!process.isAlive())
            return;

        this.initResultSet();
        List<InputStreamRunnable> runnableList = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        runnableList.add(DEF_STREAM, new InputStreamRunnable(process.getInputStream()));
        runnableList.add(ERR_STREAM, new InputStreamRunnable(process.getErrorStream()));
        threads.add(DEF_STREAM, new Thread(runnableList.get(DEF_STREAM)));
        threads.add(ERR_STREAM, new Thread(runnableList.get(ERR_STREAM)));

        // 出力データの取得（スレッド処理）
        threads.forEach(Thread::start);
        // プロセスの終了待ち
        process.waitFor(DEFAULT_PROCESS_TIMEOUT, TimeUnit.SECONDS);
        // スレッドの終了待ち
        threads.get(DEF_STREAM).join();
        threads.get(ERR_STREAM).join();

        runnableList.get(DEF_STREAM).getList().forEach(line -> this.addResult(ResultType.STANDARD, line));
        runnableList.get(ERR_STREAM).getList().forEach(line -> this.addResult(ResultType.ERROR, line));
    }

    @SuppressWarnings("unchecked")
    public Class<R> returnType() {
        Class<?> clazz = this.getClass();
        Type type = clazz.getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) type;
        Type[] actualTypeArguments = pt.getActualTypeArguments();
        return (Class<R>) actualTypeArguments[0];
    }

    public abstract R action(Arguments args);
}

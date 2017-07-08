package com.mickey305.util.cli;

import com.mickey305.util.cli.commands.GrepCommand;
import com.mickey305.util.cli.commands.LsCommand;
import com.mickey305.util.cli.commands.OpenSSLCommand;
import com.mickey305.util.cli.commands.WhichCommand;
import com.mickey305.util.cli.runtime.Executor;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.mickey305.util.system.OSCheck.isWindows;

/**
 * Created by K.Misaki on 2017/05/20.
 *
 */
public class TerminalCommandBuilder {
    private static final int DEFAULT_PROCESS_TIMEOUT = 30; // プロセスの最長実行時間（秒）

    private static Map<Class<?>, String> createCommandMap(Map<Class<?>, String> extensionDictionary) {
        Map<Class<?>, String> map = new HashMap<>();

        // search target commands mapping
        map.put(GrepCommand.class,    "grep");
        map.put(LsCommand.class,      "ls");
        map.put(OpenSSLCommand.class, "openssl");
        map.put(WhichCommand.class,   "which");

        if (extensionDictionary != null)
            map.putAll(extensionDictionary);

        return map;
    }

    public static <C> C build(Class<C> commandClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return build(commandClass, null);
    }

    public static <C> C build(Class<C> commandClass, Map<Class<?>, String> extensionDictionary) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // superclass check
        if (!TerminalCommand.class.isAssignableFrom(commandClass))
            throw new IllegalArgumentException("the super class of [" + commandClass.getName() + "] is not ["
                    + TerminalCommand.class.getName() + "]");

        Map<Class<?>, String> commandMap = createCommandMap(extensionDictionary);

        if (commandMap.containsKey(commandClass)) {
            String path = getPath(commandMap.get(commandClass));
            try {
                // absolute command path injection
                // require: single argument (type:java.lang.String) constructor
                return commandClass.getConstructor(String.class).newInstance(path);
            } catch (InstantiationException | IllegalAccessException
                    | InvocationTargetException | NoSuchMethodException e) {
                // exception throwing
                throw e;
            }
        }

        throw new IllegalArgumentException("please check the input class type");
    }

    private static String getPath(String commandName) {
        StringBuilder path = new StringBuilder();
        Executor executor = new Executor();
        executor.executeProcess(
                isWindows()
                        ? "where " + commandName
                        : "which " + commandName,
                process -> {
                    path.append(getPathFromRuntime(process));
                    return process.exitValue();
                });
        return path.toString();
    }

    private static String getPathFromRuntime(Process process) throws InterruptedException {
        if (!process.isAlive())
            return null;

        final int first = 0;
        InputStreamRunnable runnable = new InputStreamRunnable(process.getInputStream());
        Thread th = new Thread(runnable);

        // 出力データの取得（スレッド処理）
        th.start();
        // プロセスの終了待ち
        process.waitFor(DEFAULT_PROCESS_TIMEOUT, TimeUnit.SECONDS);
        // スレッドの終了待ち
        th.join();

        return runnable.getList().get(first);
    }
}

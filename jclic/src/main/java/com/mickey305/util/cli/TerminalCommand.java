package com.mickey305.util.cli;

import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.cli.model.ResultCache;
import com.mickey305.util.cli.model.ResultType;

import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public abstract class TerminalCommand extends Command implements Cloneable {
    private static final int DEF_STREAM = 0;
    private static final int ERR_STREAM = 1;
    private static final int DEFAULT_PROCESS_TIMEOUT = 120; // プロセスの最長実行時間（秒）

    private Set<ResultCache<String>> resultSet;
    private Integer pid;
    private Arguments args;
    private List<TerminalCommand> pipeCommands;

    private TerminalCommand() {
        super();
        this.initResultSet();
        this.initPipCommands();
    }

    @Override
    public TerminalCommand clone () {
        TerminalCommand scope;
        scope = (TerminalCommand) super.clone();
        scope.resultSet = new LinkedHashSet<>(this.resultSet);
        scope.args = args.clone();
        scope.pipeCommands = new ArrayList<>(this.pipeCommands);
        return scope;
    }

    protected TerminalCommand(String commandPath, String commandName) {
        this();
        File targetCommand = new File(commandPath);
        if (validateCommandPath(targetCommand, commandName)) {
            try {
                this.setArgs(new Arguments(targetCommand.getCanonicalPath()));
            } catch (IOException e) {
                this.throwException(commandPath, e);
            }
        } else {
            this.throwException(commandPath, null);
        }
    }

    protected List<TerminalCommand> getPipeCommands() {
        return pipeCommands;
    }

    private void setPipeCommands(List<TerminalCommand> pipeCommands) {
        this.pipeCommands = pipeCommands;
    }

    private void initPipCommands() {
        this.setPipeCommands(new ArrayList<>());
    }

    public TerminalCommand pipe(TerminalCommand command) {
        this.getPipeCommands().add(command);
        return this;
    }

    public TerminalCommand option(String key, String val) {
        this.getArgs().putOption(key, val);
        return this;
    }

    public TerminalCommand option(String key) {
        this.getArgs().putOption(key, null);
        return this;
    }

    public TerminalCommand options(Collection<Pair<String, String>> options) {
        options.forEach(opt -> this.getArgs().putOption(opt.getKey(), opt.getValue()));
        return this;
    }

    public TerminalCommand optionsKeyOnly(Collection<String> options) {
        options.forEach(opt -> this.getArgs().putOption(opt, null));
        return this;
    }

    public void resetOptions() {
        this.getArgs().newOptions();
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    protected void createPid(Process process) {
        if (!process.isAlive())
            return;

        try {
            Field field = process.getClass().getDeclaredField("pid");
            field.setAccessible(true);
            final int pid = field.getInt(process);
            this.setPid(pid);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Arguments getArgs() {
        return args;
    }

    public void setArgs(Arguments args) {
        this.args = args;
    }

    protected void throwException(String commandPath, Throwable th) {
        String cause = (th != null)
                ? System.lineSeparator() + "caused by " + th.getClass().getName()
                : "";
        throw new IllegalArgumentException("command PATH exception: please check the input path name."
                + System.lineSeparator() + "input = [" + commandPath + "]" + cause);
    }

    private boolean validateCommandPath(File commandFile, String commandName) {
        return commandFile.exists() && commandFile.isFile()
                && commandFile.canExecute() && commandFile.getPath().endsWith(commandName);
    }
}

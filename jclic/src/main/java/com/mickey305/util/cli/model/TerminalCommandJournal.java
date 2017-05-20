package com.mickey305.util.cli.model;

import com.mickey305.util.cli.TerminalCommandUtils;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by K.Misaki on 2017/05/20.
 *
 */
public class TerminalCommandJournal extends ModelWithLongId implements Cloneable {
    // require
    private Integer pid;
    private List<Arguments> executionArgs;
    private Map<Benchmark, Timestamp> timestampMaps;

    private Integer status;
    private Set<ResultCache<String>> resultSet;

    public TerminalCommandJournal(long id, Integer pid, List<Arguments> executionArgs,
                                  Map<Benchmark, Timestamp> timestampMaps) {
        super(id);
        this.setPid(pid);
        this.setExecutionArgs(executionArgs);
        this.setTimestampMaps(timestampMaps);
    }

    @Override
    public TerminalCommandJournal clone() {
        TerminalCommandJournal scope = null;
        try {
            scope = (TerminalCommandJournal) super.clone();
            scope.executionArgs = new ArrayList<>(this.executionArgs);
            scope.resultSet = new LinkedHashSet<>(this.resultSet);
            scope.timestampMaps = new HashMap<>(this.timestampMaps);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return scope;
    }

    public String getExecutionSentence() {
        return TerminalCommandUtils.generatePipeByArguments(this.getExecutionArgs());
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<Arguments> getExecutionArgs() {
        return executionArgs;
    }

    public void setExecutionArgs(List<Arguments> executionArgs) {
        this.executionArgs = executionArgs;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<ResultCache<String>> getResultSet() {
        return resultSet;
    }

    public void setResultSet(Set<ResultCache<String>> resultSet) {
        this.resultSet = resultSet;
    }

    public Map<Benchmark, Timestamp> getTimestampMaps() {
        return timestampMaps;
    }

    public void setTimestampMaps(Map<Benchmark, Timestamp> timestampMaps) {
        this.timestampMaps = timestampMaps;
    }
}

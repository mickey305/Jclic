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
        try {
            TerminalCommandJournal scope;
            scope = (TerminalCommandJournal) super.clone();
            // manual scoping
            scope.executionArgs = new ArrayList<>();
            scope.resultSet = new LinkedHashSet<>();
            scope.timestampMaps = new HashMap<>();
            scope.setId(this.getId());
            scope.setPid(this.getPid());
            this.executionArgs.forEach(data -> scope.executionArgs.add((data != null)
                    ? data.clone()
                    : null));
            this.resultSet.forEach(data -> scope.resultSet.add((data != null)
                    ? data.clone(seed -> seed)
                    : null));
            this.timestampMaps.forEach((k, v) -> scope.timestampMaps.put(k, (v != null)
                    ? (Timestamp) v.clone()
                    : null));
            return scope;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
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

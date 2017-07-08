package com.mickey305.util.cli.receivers;

import com.mickey305.util.cli.CliReceiver;
import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.cli.model.ResultCache;

import java.util.List;
import java.util.Set;

/**
 * Created by K.Misaki on 2017/05/20.
 *
 */
public class ResultAccessibleReceiver implements CliReceiver {
    private boolean accept;
    private List<Arguments> argsList;
    private int status;
    private Set<ResultCache<String>> resultCacheSet;

    public ResultAccessibleReceiver() {
        this.setAccept(false);
    }

    @Override
    public void action(List<Arguments> args, int status, Set<ResultCache<String>> resultSet) {
        this.setArgsList(args);
        this.setStatus(status);
        this.setResultCacheSet(resultSet);
        this.setAccept(true);
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public List<Arguments> getArgsList() {
        return argsList;
    }

    public void setArgsList(List<Arguments> argsList) {
        this.argsList = argsList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<ResultCache<String>> getResultCacheSet() {
        return resultCacheSet;
    }

    public void setResultCacheSet(Set<ResultCache<String>> resultCacheSet) {
        this.resultCacheSet = resultCacheSet;
    }
}

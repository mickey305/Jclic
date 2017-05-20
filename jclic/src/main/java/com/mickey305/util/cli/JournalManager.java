package com.mickey305.util.cli;

import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.cli.model.Benchmark;
import com.mickey305.util.cli.model.ResultCache;
import com.mickey305.util.cli.model.TerminalCommandJournal;
import com.mickey305.util.cli.receivers.ResultAccessibleReceiver;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by K.Misaki on 2017/05/20.
 *
 */
public class JournalManager {
    private static final long INIT_ID = 0;

    private List<TerminalCommandJournal> journalList;
    private long id;

    public JournalManager() {
        this.setJournalList(new ArrayList<>());
        this.setId(INIT_ID);
    }

    public void createAndAddJournal(Integer pid, ResultAccessibleReceiver receiver, Map<Benchmark, Timestamp> timestampMaps) {
        this.createAndAddJournal(pid, receiver.getArgsList(), receiver.getStatus(), receiver.getResultCacheSet(), timestampMaps);
    }

    public void createAndAddJournal(List<Arguments> executionArgs, Map<Benchmark, Timestamp> timestampMaps) {
        this.createAndAddJournal(null, executionArgs, null, null, timestampMaps);
    }

    public void createAndAddJournal(Integer pid, List<Arguments> executionArgs, Integer status,
                                    Set<ResultCache<String>> resultSet, Map<Benchmark, Timestamp> timestampMaps) {
        TerminalCommandJournal journal;
        journal = new TerminalCommandJournal(this.getAndIncrementId(), pid, executionArgs, timestampMaps);
        journal.setStatus(status);
        journal.setResultSet(resultSet);

        this.getJournalList().add(journal);
    }

    public List<TerminalCommandJournal> getJournalList() {
        return journalList;
    }

    protected void setJournalList(List<TerminalCommandJournal> journalList) {
        this.journalList = journalList;
    }

    private long getAndIncrementId() {
        return id++;
    }

    private void setId(long id) {
        this.id = id;
    }
}

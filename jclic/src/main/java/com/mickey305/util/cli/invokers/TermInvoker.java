package com.mickey305.util.cli.invokers;

import com.mickey305.util.cli.Invoker;
import com.mickey305.util.cli.JournalManager;
import com.mickey305.util.cli.CliReceiver;
import com.mickey305.util.cli.TerminalCommand;
import com.mickey305.util.cli.model.Benchmark;
import com.mickey305.util.cli.receivers.ResultAccessibleReceiver;

import java.sql.Timestamp;
import java.util.Map;

import static com.mickey305.util.cli.TerminalCommand.RESULT_ERR;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public class TermInvoker<C extends TerminalCommand> extends Invoker<C> {
    private JournalManager journalManager;

    public TermInvoker() {
        super();
    }

    public JournalManager getJournalManager() {
        return journalManager;
    }

    public void setJournalManager(JournalManager journalManager) {
        this.journalManager = journalManager;
    }

    public TermInvoker<C> journalManager(JournalManager journalManager) {
        this.journalManager = journalManager;
        return this;
    }

    @Override
    public void execute() {
        this.execute(null);
    }

    @Override
    public void execute(Callback callback) {
        JournalManager jm = this.getJournalManager();
        for (C command : this.getCommands()) {
            // execution
            int status = command.execute();

            Integer pid = command.getPid();
            CliReceiver receiver = command.getReceiver();
            Map<Benchmark, Timestamp> timestampMap = command.getTimestampMap();
            if (jm != null
                    && pid != null
                    && timestampMap != null
                    && receiver != null
                    && receiver instanceof ResultAccessibleReceiver) {
                jm.createAndAddJournal(pid, (ResultAccessibleReceiver) receiver, timestampMap);
            }
            if (callback != null)
                callback.onFinishEvent(status);
            // # execution stop judge
            // not error-ignore mode and command status code is "error"
            if (!super.isErrorIgnoreExecution() && status == RESULT_ERR)
                break;
        }
    }
}

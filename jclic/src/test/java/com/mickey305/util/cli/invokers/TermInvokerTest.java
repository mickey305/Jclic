package com.mickey305.util.cli.invokers;

import com.mickey305.util.cli.JournalManager;
import com.mickey305.util.cli.TerminalCommand;
import com.mickey305.util.cli.commands.LsCommand;
import com.mickey305.util.cli.commands.WhichCommand;
import com.mickey305.util.cli.receivers.ResultAccessibleReceiver;
import com.mickey305.util.system.OSCheck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by K.Misaki on 2017/07/08.
 *
 */
public class TermInvokerTest {
    private TermInvoker<TerminalCommand> invoker;

    @Before
    public void setUp() throws Exception {
        this.invoker = new TermInvoker<>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getJournalManager() throws Exception {
    }

    @Test
    public void setJournalManager() throws Exception {
    }

    @Test
    public void journalManager() throws Exception {
    }

    /**
     *
     * Target: {@link TermInvoker#execute()}
     */
    @Test
    public void execute() throws Exception {
        if (OSCheck.isUnix()) {
            this.executeTestForUnix();
        }
    }

    private void executeTestForUnix() throws Exception {
        final int commandCnt = 5000;
        TermInvoker<TerminalCommand> invoker = this.invoker;
        JournalManager journalManager = new JournalManager();
        ResultAccessibleReceiver receiver = new ResultAccessibleReceiver();
        TerminalCommand which = WhichCommand.create();
        LsCommand ls = LsCommand.create();
        List<TerminalCommand> commands = new ArrayList<>();
        if (which == null) return;
        if (ls == null) return;
        for (int i = 0; i < commandCnt / 2; i++)
            commands.add(which.clone().receiver(receiver).option("ls"));
        for (int i = 0; i < commandCnt / 2; i++)
            commands.add(ls.clone().receiver(receiver).option("-la"));
        invoker.addAll(commands);
        invoker.journalManager(journalManager);

        invoker.execute();

        final int actualJournalSize = invoker.getJournalManager().getJournalList().size();
//        invoker.getJournalManager().getJournalList().forEach(journal -> {
//            final String result = journal.getResultSet().stream().map(
//                    ResultCache::getResult).collect(Collectors.toList()).toString();
//            System.out.println(journal.getId()
//                    + " " + journal.getPid()
//                    + " " + journal.getExecutionSentence()
//                    + " " + journal.getTimestampMaps()
//                    + " " + result);
//        });

        assertEquals("ジャーナルが正しく集計できていない", commandCnt, actualJournalSize);
    }

    @Test
    public void execute1() throws Exception {
    }

    @Test
    public void getCommands() throws Exception {
    }

    @Test
    public void setCommands() throws Exception {
    }

    @Test
    public void isErrorIgnoreExecution() throws Exception {
    }

    @Test
    public void setErrorIgnoreExecution() throws Exception {
    }

    @Test
    public void errorIgnore() throws Exception {
    }

    @Test
    public void add() throws Exception {
    }

    @Test
    public void addAll() throws Exception {
    }

    @Test
    public void undo() throws Exception {
    }

    @Test
    public void redo() throws Exception {
    }

    @Test
    public void execute2() throws Exception {
    }

    @Test
    public void execute3() throws Exception {
    }

}
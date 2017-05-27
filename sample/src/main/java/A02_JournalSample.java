import com.mickey305.util.cli.JournalManager;
import com.mickey305.util.cli.TerminalCommand;
import com.mickey305.util.cli.commands.GrepCommand;
import com.mickey305.util.cli.commands.LsCommand;
import com.mickey305.util.cli.commands.OpenSSLCommand;
import com.mickey305.util.cli.commands.WhichCommand;
import com.mickey305.util.cli.invokers.TermInvoker;
import com.mickey305.util.cli.model.Benchmark;
import com.mickey305.util.cli.model.ResultCache;
import com.mickey305.util.cli.model.TerminalCommandJournal;
import com.mickey305.util.cli.receivers.ResultAccessibleReceiver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public class A02_JournalSample {
    protected static final String ANSI_RESET = "\u001b[0m";
    protected static final String ANSI_FONT_BLUE = "\u001b[34m";
    protected static final String ANSI_FONT_RED = "\u001b[31m";
    protected static final String ANSI_FONT_WHITE = "\u001b[37m";

    public void execute() {
        ResultAccessibleReceiver resultAccessibleReceiver = new ResultAccessibleReceiver();
        List<TerminalCommand> opensslCommands = new ArrayList<>();
        List<TerminalCommand> whichCommands = new ArrayList<>();
        List<TerminalCommand> searchingCommands = new ArrayList<>();
        OpenSSLCommand openssl = OpenSSLCommand.create();
        WhichCommand which = WhichCommand.create();
        LsCommand ls = LsCommand.create();
        GrepCommand grep = GrepCommand.create();
        opensslCommands.add(openssl.clone().option("version"));
        opensslCommands.add(openssl.clone().option("version"));
        opensslCommands.add(openssl.clone().option("version"));
        whichCommands.add(which.clone().option("openssl"));
        whichCommands.add(which.clone().option("java"));
        whichCommands.add(which.clone().option("scala"));
        whichCommands.add(which.clone().option("which"));
        searchingCommands.add(ls.clone().option("-la").option("."));
        searchingCommands.add(ls.clone().option("-la").option(".")
                .pipe(grep.clone().option("gradle")));

        opensslCommands.forEach(cmd -> cmd.setReceiver(resultAccessibleReceiver));
        searchingCommands.forEach(cmd -> cmd.setReceiver(resultAccessibleReceiver));
        whichCommands.forEach(cmd -> cmd.setReceiver(resultAccessibleReceiver));

        TermInvoker<TerminalCommand> invoker = new TermInvoker<>();
        invoker.setJournalManager(new JournalManager());
        invoker.addAll(opensslCommands);
        invoker.addAll(searchingCommands);
        invoker.addAll(whichCommands);

        //-------------------------------------------------------------------------------------------------------------+
        // Terminal Command Post                                                                                       |
        //-------------------------------------------------------------------------------------------------------------+
        // execute
        invoker.execute();
        List<TerminalCommandJournal> journalList = invoker.getJournalManager().getJournalList();
        // print
        printJournal(journalList);

        invoker.undo();
        invoker.undo();
        invoker.redo();
        System.out.println("========== Undo ==========");
        System.out.println("========== Undo ==========");
        System.out.println("========== Redo ==========");

        // execute
        invoker.execute();
        journalList = invoker.getJournalManager().getJournalList();
        // print
        printJournal(journalList);
        // sort
        journalList = invoker.getJournalManager().getJournalList().stream().sorted(
                Comparator.comparing(TerminalCommandJournal::getId).reversed()).collect(Collectors.toList());
        // print
        printJournal(journalList);
        // clear
        journalList.clear();
        // add
        journalList.add(invoker.getJournalManager().getFirstJournal());
        journalList.add(invoker.getJournalManager().getLastJournal());
        // print
        printJournal(journalList);
    }

    private void printJournal(List<TerminalCommandJournal> journalList) {
        System.out.println("+-------------------- Journal Print --------------------+");
        String comma =  ANSI_FONT_RED + "," + ANSI_RESET;
        System.out.println(colorBlue("ID") + "\t" + comma
                + colorBlue("PID") + "\t" + comma
                + colorBlue("Timestamps") + comma
                + colorBlue("ExecutionSentence") + comma
                + colorBlue("STATUS") + comma
                + colorBlue("ResultSet"));
        journalList.forEach(journal -> {
            StringBuilder resultString = new StringBuilder();
            int i = 0;
            String endPoint = ",";
            resultString.append("[");
            for (ResultCache<String> resultCache: journal.getResultSet()) {
                i++;
                if (i == journal.getResultSet().size())
                    endPoint = "";
                resultString
                        .append("{\"")
                        .append(ANSI_FONT_WHITE).append(resultCache.getType()).append(ANSI_FONT_BLUE)
                        .append("\":\"")
                        .append(ANSI_FONT_WHITE).append(resultCache.getResult()).append(ANSI_FONT_BLUE)
                        .append("\"}")
                        .append(endPoint);
            }
            resultString.append("]");
            System.out.println(colorBlue(journal.getId()+"") + "\t" + comma
                    + colorBlue(journal.getPid().toString()) + "\t" + comma
                    + colorBlue("[" + journal.getTimestampMaps().get(Benchmark.START) + ","
                    + journal.getTimestampMaps().get(Benchmark.END) + "]") + comma
                    + colorBlue(journal.getExecutionSentence()) + comma
                    + colorBlue(journal.getStatus().toString()) + comma
                    + colorBlue(resultString.toString()));
        });
        System.out.println("+-------------------------------------------------------+");
    }

    private String colorBlue(String msg) {
        return ANSI_FONT_BLUE + msg + ANSI_RESET;
    }
}

import com.mickey305.util.cli.*;
import com.mickey305.util.cli.commands.GrepCommand;
import com.mickey305.util.cli.commands.LsCommand;
import com.mickey305.util.cli.commands.OpenSSLCommand;
import com.mickey305.util.cli.commands.WhichCommand;
import com.mickey305.util.cli.model.ResultCache;
import com.mickey305.util.cli.model.ResultType;
import com.mickey305.util.cli.receivers.ExecutionShowReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.mickey305.util.cli.TerminalCommandBuilder.build;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public class SampleMain {
    public static void main(String[] args) {
        Receiver executionShowReceiver = new ExecutionShowReceiver();
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

        opensslCommands.forEach(cmd -> cmd.setReceiver(executionShowReceiver));
        searchingCommands.forEach(cmd -> cmd.setReceiver(executionShowReceiver));
        whichCommands.forEach(cmd -> cmd.setReceiver(executionShowReceiver));

        Invoker<TerminalCommand> invoker = new Invoker<>();
        invoker.addAll(opensslCommands);
        invoker.addAll(searchingCommands);
        invoker.addAll(whichCommands);

        //-------------------------------------------------------------------------------------------------------------+
        // Terminal Command Post                                                                                       |
        //-------------------------------------------------------------------------------------------------------------+
        invoker.execute();

        invoker.undo();
        invoker.undo();
        invoker.redo();
        System.out.println("========== Undo ==========");
        System.out.println("========== Undo ==========");
        System.out.println("========== Redo ==========");

        invoker.execute();

        //-------------------------------------------------------------------------------------------------------------+
        // Plot Result                                                                                                 |
        //-------------------------------------------------------------------------------------------------------------+
        Set<ResultCache<String>> results = opensslCommands.get(1).getResultSet();
        results.stream()
                .filter(result -> result.getType() == ResultType.STANDARD)
                .forEach(result -> System.out.println(">>>>>>>> " + result.getResult()));
    }
}

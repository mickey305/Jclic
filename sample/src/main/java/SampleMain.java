import com.mickey305.util.cli.TerminalCommand;
import com.mickey305.util.cli.Invoker;
import com.mickey305.util.cli.Receiver;
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
        opensslCommands.add(new OpenSSLCommand("/usr/local/opt/openssl/bin/openssl").option("version"));
        opensslCommands.add(new OpenSSLCommand("/usr/local/opt/openssl/bin/openssl").option("version"));
        opensslCommands.add(new OpenSSLCommand("/usr/local/opt/openssl/bin/openssl").option("version"));
        whichCommands.add(new WhichCommand("/usr/bin/which").option("openssl"));
        whichCommands.add(new WhichCommand("/usr/bin/which").option("java"));
        whichCommands.add(new WhichCommand("/usr/bin/which").option("scala"));
        whichCommands.add(new WhichCommand("/usr/bin/which").option("which"));
        searchingCommands.add(new LsCommand("/bin/ls")
                .option("-la").option("."));
        searchingCommands.add(new LsCommand("/bin/ls")
                .option("-la").option(".")
                .pipe(new GrepCommand("/usr/bin/grep").option("gradle")));

//        opensslCommands.forEach(cmd -> cmd.setReceiver(executionShowReceiver));
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

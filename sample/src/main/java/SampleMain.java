import com.mickey305.util.cli.Command;
import com.mickey305.util.cli.Invoker;
import com.mickey305.util.cli.Receiver;
import com.mickey305.util.cli.collection.BinaryArrayList;
import com.mickey305.util.cli.collection.CollectionUtils;
import com.mickey305.util.cli.commands.LsCommand;
import com.mickey305.util.cli.commands.OpenSSLCommand;
import com.mickey305.util.cli.commands.WhichCommand;
import com.mickey305.util.cli.model.ResultCache;
import com.mickey305.util.cli.model.ResultType;
import com.mickey305.util.cli.receivers.ls.ListReceiver;
import com.mickey305.util.cli.receivers.ls.ListShowReceiver;
import com.mickey305.util.cli.receivers.openssl.VersionReceiver;
import com.mickey305.util.cli.receivers.openssl.VersionShowReceiver;
import com.mickey305.util.cli.receivers.which.JavaReceiver;
import com.mickey305.util.cli.receivers.which.JavaShowReceiver;
import com.mickey305.util.cli.receivers.which.OpenSSLReceiver;
import com.mickey305.util.cli.receivers.which.OpenSSLShowReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public class SampleMain {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Receiver<Void> versionShowReceiver = new VersionShowReceiver();
        Receiver<Integer> versionReceiver = new VersionReceiver();
        Receiver<Integer> javaShowReceiver = new JavaShowReceiver();
        Receiver<Integer> javaReceiver = new JavaReceiver();
        Receiver<Integer> opensslShowReceiver = new OpenSSLShowReceiver();
        Receiver<Integer> opensslReceiver = new OpenSSLReceiver();
        Receiver<Integer> listShowReceiver = new ListShowReceiver();
        Receiver<Void> listReceiver = new ListReceiver();
        List<Receiver<Integer>> whichReceivers = new ArrayList<>();
        whichReceivers.add(javaReceiver);
        whichReceivers.add(opensslShowReceiver);
        whichReceivers.add(opensslShowReceiver);
        whichReceivers.add(opensslShowReceiver);

        List<Command<Void>> opensslCommands = new ArrayList<>();
        List<Command<Integer>> whichCommands = new ArrayList<>();
        List<Command<Void>> lsCommands = new ArrayList<>();
        opensslCommands.add(new OpenSSLCommand<>("/usr/local/opt/openssl/bin/openssl"));
        opensslCommands.add(new OpenSSLCommand<>("/usr/local/opt/openssl/bin/openssl"));
        opensslCommands.add(new OpenSSLCommand<>("/usr/local/opt/openssl/bin/openssl"));
        whichCommands.add(new WhichCommand<>("/usr/bin/which"));
        whichCommands.add(new WhichCommand<>("/usr/bin/which"));
        whichCommands.add(new WhichCommand<>("/usr/bin/which"));
        whichCommands.add(new WhichCommand<>("/usr/bin/which"));
        lsCommands.add(new LsCommand<>("/bin/ls"));
        lsCommands.add(new LsCommand<>("/bin/ls"));

        opensslCommands.forEach(command -> command.setReceiver(versionShowReceiver));
        lsCommands.forEach(command -> command.setReceiver(listReceiver));
        CollectionUtils.zip(whichCommands, whichReceivers, BinaryArrayList::createFromList)
                .forEach(Command::setReceiver);

        Invoker invoker = new Invoker<>();
        invoker.addAllCommands(opensslCommands);
        invoker.addAllCommands(lsCommands);
        invoker.addAllCommands(whichCommands);

        //-------------------------------------------------------------------------------------------------------------+
        // Command Post                                                                                                |
        //-------------------------------------------------------------------------------------------------------------+
        invoker.execute();

        invoker.undoCommand();
        invoker.undoCommand();
        invoker.redoCommand();
        System.out.println("========== Undo ==========");
        System.out.println("========== Undo ==========");
        System.out.println("========== Redo ==========");

        invoker.execute();

        //-------------------------------------------------------------------------------------------------------------+
        // Plot Result                                                                                                 |
        //-------------------------------------------------------------------------------------------------------------+
        Set<ResultCache<String>> results = listReceiver.getResultSet();
        results.stream()
                .filter(result -> result.getType() == ResultType.STANDARD)
                .forEach(result -> System.out.println(">>>>>>>> " + result.getResult()));
    }
}

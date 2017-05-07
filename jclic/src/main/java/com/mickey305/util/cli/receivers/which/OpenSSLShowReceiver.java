package com.mickey305.util.cli.receivers.which;

import com.mickey305.util.cli.Command;
import com.mickey305.util.cli.Receiver;
import com.mickey305.util.cli.model.Arguments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public class OpenSSLShowReceiver extends Receiver<Integer> {
    private static final String ANSI_RESET = "\u001b[0m";
    private static final String ANSI_FONT_RED = "\u001b[31m";

    @Override
    public Integer action(Arguments args) {
        int returnCode = Command.RESULT_ERR;

        args.putOption("openssl", null);

        System.out.println("+------------------------------ OpenSSL Show ------------------------------+");
        System.out.println("execution command => " + args.flatten());

        Runtime rt = Runtime.getRuntime();
        try {
            Process process = rt.exec(args.flatten());
            process.waitFor();
            try (InputStream is = process.getInputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is));
                 InputStream eis = process.getErrorStream();
                 BufferedReader ebr = new BufferedReader(new InputStreamReader(eis))) {
                String head;
                String line;
                head = "standard output   => ";
                while ((line = br.readLine()) != null) {
                    System.out.println(head + line);
                    head = "                     ";
                }
                head = "error output      => ";
                while ((line = ebr.readLine()) != null) {
                    System.out.println(ANSI_FONT_RED + head + line + ANSI_RESET);
                    head = "                     ";
                }
            }
            returnCode = process.exitValue();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("+--------------------------------------------------------------------------+");

        return returnCode;
    }
}

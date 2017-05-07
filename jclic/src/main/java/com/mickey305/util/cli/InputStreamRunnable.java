package com.mickey305.util.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by K.Misaki on 2017/05/07.
 *
 */
public class InputStreamRunnable implements Runnable {
    private BufferedReader bufferedReader;
    private List<String> list;

    public InputStreamRunnable(InputStream is) {
        this.setBufferedReader(new BufferedReader(new InputStreamReader(is)));
        this.setList(new ArrayList<>());
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = this.getBufferedReader().readLine()) != null)
                this.getList().add(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.getBufferedReader().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}

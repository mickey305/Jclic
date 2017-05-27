package com.mickey305.util.cli;

import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.cli.model.ResultCache;

import java.util.List;
import java.util.Set;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public interface CliReceiver {
    void action(List<Arguments> args, int status, Set<ResultCache<String>> resultSet);
}

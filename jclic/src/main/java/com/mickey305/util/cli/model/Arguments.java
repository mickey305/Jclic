package com.mickey305.util.cli.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public class Arguments implements Cloneable {
    private String commandPath;
    private Map<String, String> options;

    public Arguments(String commandPath) {
        this.newOptions();
        this.setCommandPath(commandPath);
    }

    @Override
    public Arguments clone() {
        try {
            Arguments scope;
            scope = (Arguments) super.clone();
            // manual scoping
            scope.options = new LinkedHashMap<>();
            scope.setCommandPath(this.getCommandPath());
            this.options.forEach((k, v) -> scope.options.put(k, v));
            return scope;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCommandPath() {
        return commandPath;
    }

    private void setCommandPath(String commandPath) {
        this.commandPath = commandPath;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public String putOption(String op, String param) {
        return getOptions().put(op, param);
    }

    public String removeOption(String op) {
        return getOptions().remove(op);
    }

    public String getParam(String op) {
        return getOptions().get(op);
    }

    public void newOptions() {
        this.setOptions(new LinkedHashMap<>());
    }

    public String flatten() {
        StringBuilder lineOption = new StringBuilder();
        getOptions().forEach((key, value) -> {
            if (!key.isEmpty())
                lineOption.append(key).append(" ");
            if (value != null && !value.isEmpty())
                lineOption.append(value).append(" ");
        });
        return getCommandPath() + " " + lineOption.toString();
    }
}

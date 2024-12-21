package com.neolcr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlNodeTree {
    String value;
    Type type;
    String previousValue;
    String nextValue;
    Type previousType;
    Type nextType;
    Pattern pattern = Pattern.compile("a-zA-Z0-9", Pattern.CASE_INSENSITIVE);
    Matcher matcher = null;

    public SqlNodeTree(String value, Type type, String previousValue, String nextValue) {
        assert (value != null);
        assert (type != null);
        this.value = value;
        this.type = type;
        this.previousValue = previousValue;
        this.nextValue = nextValue;
    }

    public SqlNodeTree(String value, Type type, Type previousType, Type nextType) {
        assert (value != null);
        assert (type != null);
        this.value = value;
        this.type = type;
        this.previousType = previousType;
        this.nextType = nextType;
    }

    private boolean matches() {
        this.matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public Type getTipo() {
        return type;
    }

    public void setTipo(Type type) {
        this.type = type;
    }

    public String getNextValue() {
        return nextValue;
    }

    public void setNextValue(String nextValue) {
        this.nextValue = nextValue;
    }

    public String getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(String previousValue) {
        this.previousValue = previousValue;
    }
}

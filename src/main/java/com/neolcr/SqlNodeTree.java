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
    SqlNodeTree child;

    @Override
    public String toString() {
        return "{" +
                "value='" + value + '\'' +
                '}';
    }

    public SqlNodeTree() {
        child = new SqlNodeTree();
    }
    public SqlNodeTree(String value, Type type, String previousValue, String nextValue) {
        this();
        assert (value != null);
        assert (type != null);
        this.value = value;
        this.type = type;
        this.previousValue = previousValue;
        this.nextValue = nextValue;
    }

    public SqlNodeTree getChild() {
        return child;
    }

    public void setChild(SqlNodeTree child) {
        this.child = child;
    }

    public SqlNodeTree(String value, Type type, Type previousType, Type nextType) {
        this();
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


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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

    public Type getPreviousType() {
        return previousType;
    }

    public void setPreviousType(Type previousType) {
        this.previousType = previousType;
    }

    public Type getNextType() {
        return nextType;
    }

    public void setNextType(Type nextType) {
        this.nextType = nextType;
    }
}

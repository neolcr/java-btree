package com.neolcr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<SqlNodeTree>  list = new ArrayList<>();
        list.add(new SqlNodeTree("SELECT", Type.SELECT,null, Type.AST));
        list.add(new SqlNodeTree("SELECT", Type.SELECT,null, Type.TABLE));
        list.add(new SqlNodeTree("*", Type.AST, Type.SELECT, Type.FROM));
        list.add(new SqlNodeTree("FROM", Type.FROM,"SELECT", "FROM"));
        list.add(new SqlNodeTree("", Type.TABLE, Type.FROM, Type.SEMICOLON));

        List<String> strings = Arrays.asList("SELECT", "*", "FROM", "tabla1", ";");
        int index = 0;
        List<SqlNodeTree> collected = new ArrayList<>();
        List<SqlNodeTree> found = new ArrayList<>();
        for (String elemento : strings) {
            if (index == 0) {
                found = list.stream()
                        .filter(it -> it.value.equals(elemento)).collect(Collectors.toList());

            } else {
                found = collected.stream()
                        .filter(it -> it.value.equals(elemento)).collect(Collectors.toList());
            }
            index++;

            if (found.isEmpty()) {
                throw new RuntimeException(("El elemento no existe: " + elemento));
            }
            List<Type> nextTypes = found.stream().map(it -> it.nextType).collect(Collectors.toList());
            List<String> nextValues = found.stream().map(it -> it.nextValue).collect(Collectors.toList());
            collected = list.stream().filter(it -> nextTypes.contains(it.type) || nextValues.contains(it.value)).collect(Collectors.toList());

        }

    }

    private void ejemploInt() {
        BinaryTree tree = new BinaryTree();
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(10);
        tree.insert(80);
        tree.insert(16);
        tree.insert(60);
        tree.insert(20);
        tree.insert(100);
        tree.insert(65);
        tree.insert(5);
        tree.insert(15);

        System.out.println("In-order traversal of the binary tree:");
        tree.inorderTraversal();
    }

}
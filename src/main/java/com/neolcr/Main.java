package com.neolcr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<SqlNodeTree>  list = new ArrayList<>();
        list.add(new SqlNodeTree("SELECT", Type.SELECT,null, Type.AST));
        list.add(new SqlNodeTree("SELECT", Type.SELECT,null, Type.TABLE));
        list.add(new SqlNodeTree("*", Type.AST, Type.SELECT, Type.FROM));
        list.add(new SqlNodeTree("FROM", Type.FROM, Type.SELECT, Type.TABLE));
        list.add(new SqlNodeTree("", Type.TABLE, Type.FROM, Type.SEMICOLON));

        List<String> elements = new ArrayList<>(List.of("SELECT", "*", "FROM", "tabla1", ";"));
        List<SqlNodeTree> collected = new ArrayList<>();
        List<SqlNodeTree> found = list.stream()
                .filter(it -> it.value.equals(list.getFirst().value)).collect(Collectors.toList());

        final List<Type> firstNextTypes = found.stream().map(it -> it.nextType).toList();
        final List<String> firstNextValues = found.stream().map(it -> it.nextValue).toList();
        collected = list.stream().filter(it -> firstNextTypes.contains(it.type) || firstNextValues.contains(it.value)).toList();
        elements.removeFirst();

        for (String element : elements) {
             found = collected.stream()
                        .filter(it -> it.value.equals(element)).toList();
            final List<Type> nextTypes = found.stream().map(it -> it.nextType).toList();
            final List<String> nextValues = found.stream().map(it -> it.nextValue).toList();
            if (found.isEmpty()) {
                List<SqlNodeTree> tables = collected.stream().filter(it -> it.type.equals(Type.TABLE)).toList();
                if (tables.size() > 1) {
                    throw new RuntimeException("No tiene sentido que haya mas de una tabla");
                }
                SqlNodeTree table = tables.getFirst();
                if (table.getPreviousType().equals(Type.FROM)) {
                    table.setValue(element);
                } else {
                    throw new RuntimeException("Encontrada declaracion de tabla sin FROM");
                }
                //throw new RuntimeException(("El element no existe: " + element));
            }
            collected = list.stream().filter(it -> nextTypes.contains(it.type) || nextValues.contains(it.value)).toList();

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
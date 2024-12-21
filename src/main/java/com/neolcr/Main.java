package com.neolcr;

import java.util.ArrayList;
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
        list.add(new SqlNodeTree(";", Type.SEMICOLON, Type.TABLE, Type.END));

        List<String> elements = new ArrayList<>(List.of("SELECT", "*", "FROM", "tabla1", ";"));
        List<SqlNodeTree> collected;
        List<SqlNodeTree> found = list.stream()
                .filter(it -> it.value.equals(list.getFirst().value)).collect(Collectors.toList());

        final List<Type> firstNextTypes = found.stream().map(it -> it.nextType).toList();
        final List<String> firstNextValues = found.stream().map(it -> it.nextValue).toList();
        collected = list.stream().filter(it -> firstNextTypes.contains(it.type) || firstNextValues.contains(it.value)).toList();

        SqlNodeTree root = new SqlNodeTree();
        root.setChild(found.getFirst());;
        elements.removeFirst();
        for (String element : elements) {
            found = collected.stream()
                        .filter(it -> it.value.equals(element)).toList();
            if (found.isEmpty()) {
                found = collected.stream()
                        .filter(it -> it.type.equals(Type.TABLE)).toList();
            }

            final List<Type> nextTypes = found.stream().map(it -> it.nextType).toList();
            final List<String> nextValues = found.stream().map(it -> it.nextValue).toList();

            if (found.isEmpty()) {
                List<SqlNodeTree> tables = collected.stream().filter(it -> it.type.equals(Type.TABLE)).toList();

                if (tables.size() > 1) {
                    throw new RuntimeException("No tiene sentido que haya mas de una tabla");
                } else if (tables.isEmpty()){
                    System.out.println("Element: " + element);
                    throw new RuntimeException("No tiene sentido que ninguna sea tabla");
                } else {
                    SqlNodeTree table = tables.getFirst();
                    if (table.getPreviousType().equals(Type.FROM)) {
                        table.setValue(element);
                    } else {
                        throw new RuntimeException("Encontrada declaracion de tabla sin FROM");
                    }
                    //throw new RuntimeException(("El element no existe: " + element));
                }
            }
            collected = list.stream().filter(it -> nextTypes.contains(it.type) || nextValues.contains(it.value)).toList();

        }
        System.out.println(root);
    }
}
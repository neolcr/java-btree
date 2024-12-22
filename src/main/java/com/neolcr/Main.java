package com.neolcr;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        List<SqlNodeTree>  list = new ArrayList<>();
        list.add(new SqlNodeTree("SELECT", Type.SELECT,null, Type.AST));
        list.add(new SqlNodeTree("SELECT", Type.SELECT,null, Type.TABLE));
        list.add(new SqlNodeTree("*", Type.AST, Type.SELECT, Type.FROM));
        list.add(new SqlNodeTree("FROM", Type.FROM, Type.SELECT, Type.TABLE));
        list.add(new SqlNodeTree("", Type.TABLE, Type.FROM, Type.SEMICOLON));
        list.add(new SqlNodeTree(";", Type.SEMICOLON, Type.TABLE, Type.END));

        List<String> elements = new ArrayList<>(List.of("SELECT", "*", "FROM", "tabla1", ";"));
        List<SqlNodeTree> futuresMatchingValueOrType;
        List<SqlNodeTree> matchCurrentValue = list.stream()
                .filter(it -> it.value.equals(list.getFirst().value)).collect(Collectors.toList());

        final List<Type> firstNextTypes = matchCurrentValue.stream().map(it -> it.nextType).toList();
        final List<String> firstNextValues = matchCurrentValue.stream().map(it -> it.nextValue).toList();
        futuresMatchingValueOrType = list.stream().filter(it -> firstNextTypes.contains(it.type) || firstNextValues.contains(it.value)).toList();
        List<SqlNodeTree> finalStructure = new ArrayList<>();
        SqlNodeTree root = new SqlNodeTree(Type.ROOT);
        finalStructure.add(root);
        elements.removeFirst();

        finalStructure.add(matchCurrentValue.getFirst());
        for (String element : elements) {
            matchCurrentValue = futuresMatchingValueOrType.stream()
                        .filter(it -> it.value.equals(element)).toList();
            if (matchCurrentValue.isEmpty()) {
                matchCurrentValue = futuresMatchingValueOrType.stream()
                        .filter(it -> it.type.equals(Type.TABLE)).toList();
            }
            final List<Type> nextTypes = matchCurrentValue.stream().map(it -> it.nextType).toList();
            final List<String> nextValues = matchCurrentValue.stream().map(it -> it.nextValue).toList();

            pushNode(root, matchCurrentValue.getFirst());
            if (matchCurrentValue.isEmpty()) {
                List<SqlNodeTree> tables = futuresMatchingValueOrType.stream().filter(it -> it.type.equals(Type.TABLE)).toList();

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

            futuresMatchingValueOrType = list.stream().filter(it -> nextTypes.contains(it.type) || nextValues.contains(it.value)).toList();

            if (futuresMatchingValueOrType.isEmpty()) {
                System.out.println("Collected está vacio");
            } else if (futuresMatchingValueOrType.size() > 1) {
                System.out.println("Collected tiene mas de un elemento: " + futuresMatchingValueOrType);
            }
        }
        logger.info("Final structure: " + root);
    }

    static void pushNode(SqlNodeTree root, SqlNodeTree child) {
        if (root.child == null){
            root.setChild(child);
            return;
        }
        pushNode(root.getChild(), child);

    }
}
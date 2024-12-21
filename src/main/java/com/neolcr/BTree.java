package com.neolcr;

class Node {
    int value;
    Node left, right;

    public Node(int value) {
        this.value = value;
        left = right = null;
    }
}
class BinaryTree {
    Node root;

    public BinaryTree() {
        root = null;
    }

    public void insert(int value){
        root = insertRec(root, value);
    }
    // recursive function to insert a node
    private Node insertRec(Node root, int value){
        if (root == null) {
            root = new Node(value);
            return root;
        }
        if (value < root.value) {
            root.left = insertRec(root.left, value);
        } else if(value > root.value){
            root.right = insertRec(root.right, value);
        }
        return  root;
    }

    // in-order traversal (left, root, right)
    public void inorderTraversal() {
        inorderRec(root);
    }

    public void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.value);
            inorderRec(root.right);
        }
    }

}



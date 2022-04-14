package se.su.joos1190;

import java.util.*;

/**
 * Store course information in a binary search tree
 */
public class BinarySearchTree<Key extends Comparable<Key>, Value>
    implements Iterable<BinarySearchTree<Key, Value>.BSTNode>{
    /**
     * Attributes
     */
    BSTNode root = null;      // The root of the tree. Start with an empty tree.

    public BinarySearchTree() {
        // Empty constructor?
    }

    /**
     * Public interface for inserting data into the datastructure. Utilizes
     * a private, more technical method.
     *
     * @param key,    eg "DA3018"
     * @param value,    eg "Computer Science"
     */
    public void insert(Key key, Value value) {
        if (root == null) {
            root = new BSTNode(key, value);
            return;
        }

        root = insert(root, key, value); // Call to private method with the same name, but other parameters
    }

    /**
     * Insert 'node' into the tree pointed at by 'root'.
     *
     * @param node
     * @param key
     * @param value
     * @return The node that should be the root of this subtree.
     */
    private BSTNode insert(BSTNode node, Key key, Value value) {
        if (node == null) {
            return new BSTNode(key, value); // The easy case. Let the current node be the root of a new (sub?)tree.
        } else {
            var left = node.getLeft();
            var right = node.getRight();

            var cmp = key.compareTo(node.getKey());

            if      (cmp < 0) node.setLeft(insert(left, key, value));
            else if (cmp > 0) node.setRight(insert(right, key, value));
            else              node.setValue(value);

            return node;
        }
    }

    /**
     * size: Count the number of nodes in the search tree
     */

    public int size() {
        return size(root);
    }

    private int size(BSTNode node) {
        if (node == null) {
            return 0;
        }

        return 1 + size(node.getLeft()) + size(node.getRight());
    }

    /**
     * find: Find a course given a course code
     */
    public BSTNode find(Key key) {
        return find(root, key);
    }

    public BSTNode find(BSTNode node, Key key) {
        if (node == null) return null;

        var cmp = key.compareTo(node.getKey());
        if (cmp < 0) return find(node.getLeft(), key);
        else if (cmp == 0) return node;
        else return find(node.getRight(), key);
    }

    public void remove(Key key) {
        root = remove(root, key);
    }

    // Extract the minimal (left-most) node.
    private BSTNode extractMin(BSTNode parent, BSTNode node) {
        if (node == null) {
            throw new NoSuchElementException("min: Trying to extract minimum node of empty node.");
        }

        if (node.getLeft() == null) {
            parent.setLeft(node.getRight());
            return node;
        }

        return extractMin(node, node.getLeft());
    }

    private BSTNode remove(BSTNode node, Key key) {
        if (node == null) {
            throw new NoSuchElementException("remove: Trying to remove empty node.");
        }

        int comparison = key.compareTo(node.getKey());
        if (comparison < 0) {
            node.setLeft(remove(node.getLeft(), key));
            return node;
        } else if (comparison > 0) {
            node.setRight(remove(node.getRight(), key));
            return node;
        } else {
            var left = node.getLeft();
            var right = node.getRight();
            if (left == null) {
                return right;
            }
            if (right == null) {
                return left;
            }

            var least = extractMin(node, right);
            if (least == right) {
                least.setChildren(left, right.getRight());
            } else {
                least.setChildren(left, right);
            }

            return least;
        }
    }


    public static void main(String[] args)
    {
        BinarySearchTree<String, String> tree = new BinarySearchTree<>();
        tree.insert("DA3018", "Datalogi");
        tree.insert("MM2001", "Matematik I");
        tree.insert("DA2004", "Programmeringsteknik");
        tree.insert("DA4002", "Mjukvaruutveckling");
        tree.insert("DA4004", "Introduktion till maskininlarning");
        tree.insert("DA4005", "Algoritmer och komplexitet");
        tree.insert("MM5016", "Numerisk analys I");
        tree.insert("DA5001", "Numerisk analys II");
        tree.insert("DA4003", "Programmeringsparadigm");
        tree.insert("DA4001", "Databasteknik I");
        int c = 0;

        for (var x: tree) {
            c++;
            System.out.format("%s - %s\n", x.getKey(), x.getValue());
        }

        System.out.format("Total: %d\n\nRemove DA3018 && DA4005\n\n", c);
        tree.remove("DA3018");
        tree.remove("DA4005");

        c = 0;

        for (var x: tree) {
            c++;
            System.out.format("%s - %s\n", x.getKey(), x.getValue());
        }

        System.out.format("Total: %d", c);

    }

    @Override
    public Iterator<BSTNode> iterator() {
        return new BSTIterator(root);
    }

    private class BSTIterator implements Iterator<BSTNode> {
        private class Link {
            Link(BSTNode node, Link next) {
                this.node = node;
                this.next = next;
            }
            BSTNode node = null;
            Link next = null;
        }

        Link stack;

        private void push(BSTNode node) {
            if (stack == null) {
                stack = new Link(node, null);
            } else {
                var current = stack;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = new Link(node, null);
            }
        }

        private BSTNode pop() {
            if (stack == null) {
                return null;
            }

            var result = stack.node;
            stack = stack.next;
            return result;
        }

        BSTIterator(BSTNode node) {
            stack = new Link(node, null);
        }

        @Override
        public boolean hasNext() {
            return stack != null;
        }

        @Override
        public BSTNode next() {
            var result = pop();
            if (result == null) throw new NoSuchElementException();

            if (result.getLeft() != null) push(result.getLeft());
            if (result.getRight() != null) push(result.getRight());

            return result;
        }
    }

    /**
     * Nodes in the search tree
     * This class should be sufficient for the project.
     */
    public class BSTNode {
        final private Key key;
        private Value value;
        private BSTNode left = null;
        private BSTNode right = null;

        /**
         * Constructor
         */
        public BSTNode(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        public void setLeft(BSTNode left) {
            this.left = left;
        }
        public void setRight(BSTNode right) {
            this.right = right;
        }

        /**
         * Specify the children of the current node
         *
         * @param left
         * @param right
         */
        public void setChildren(BSTNode left, BSTNode right) {
            this.left = left;
            this.right = right;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }
        public void setValue(Value value) {
            this.value = value;
        }
        public BSTNode getLeft() {
            return left;
        }

        public BSTNode getRight() {
            return right;
        }
    }
}

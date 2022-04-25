package se.su.joos1190;

import java.util.*;
import java.util.function.Supplier;

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

    ///////////////////////////////////////////////
    /////////////////   PART 1   //////////////////
    ///////////////////////////////////////////////
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
     * I switched to a generic implementation to avoid a bug where in the stub code given,
     * inserting a node with children could break the search tree property.
     *
     * @param node the node to insert into
     * @param key the key
     * @param value the value to insert
     * @return The node that should be the root of this subtree.
     */
    private BSTNode insert(BSTNode node, Key key, Value value) {
        if (node == null) {
            return new BSTNode(key, value);
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

    // Find the node with key.
    public BSTNode find(BSTNode node, Key key) {
        if (node == null) return null;

        var cmp = key.compareTo(node.getKey());
        if (cmp < 0) return find(node.getLeft(), key);
        else if (cmp == 0) return node;
        else return find(node.getRight(), key);
    }

    ///////////////////////////////////////////////
    /////////////////   PART 2   //////////////////
    ///////////////////////////////////////////////

    @Override
    public Iterator<BSTNode> iterator() {
        return new ContIterator(root);
    }

    private class ContIterator implements Iterator<BSTNode> {
        record ContResult<R>(R node, Cont<R> cont) {}

        @FunctionalInterface
        interface Cont<R> {
            ContResult<R> run();
        }

        Cont<BSTNode> once(BSTNode node) {
            return () -> new ContResult<>(node, null);
        }

        Cont<BSTNode> chain(Cont<BSTNode> first, Supplier<Cont<BSTNode>> next) {
            if (first == null) {
                return next.get();
            } else {
                return () -> {
                    var result = first.run();
                    return new ContResult<>(result.node, result.cont == null ? next.get() : chain(result.cont, next));
                };
            }
        }

        Cont<BSTNode> current;

        Cont<BSTNode> preorder(BSTNode node) {
            if (node == null) return null;
            return chain(once(node), () ->
                    chain(preorder(node.left), () ->
                      preorder(node.right)));
        }

        ContIterator(BSTNode start) {
            current = preorder(start);
        }


        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public BSTNode next() {
            var result = current.run();
            current = result.cont;
            return result.node;
        }
    }

    ///////////////////////////////////////////////
    /////////////////   PART 4   //////////////////
    ///////////////////////////////////////////////

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

    /**
     * Nodes in the search tree
     * Uses a generic representation for more correct implementation
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

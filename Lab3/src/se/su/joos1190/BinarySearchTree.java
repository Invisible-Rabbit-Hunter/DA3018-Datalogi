package se.su.joos1190;

import java.util.*;

/**
 * Store course information in a binary search tree
 */
public class BinarySearchTree implements Iterable<BinarySearchTree.BSTNode> {
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
     * @param courseCode,    eg "DA3018"
     * @param courseName,    eg "Computer Science"
     * @param courseCredits, eg 7,5
     */
    public void insert(String courseCode, String courseName, double courseCredits) {
        BSTNode node = new BSTNode(courseCode, courseName, courseCredits);
        root = insert(root, node); // Call to private method with the same name, but other parameters
    }

    /**
     * Insert 'node' into the tree pointed at by 'root'.
     *
     * @param root
     * @param node WARNING! This method has a bug, it does not behave according to specification!
     * @returns The node that should be the root of this subtree.
     */
    private BSTNode insert(BSTNode root, BSTNode node) {
        if (root == null) {
            return node; // The easy case. Let the current node be the root of a new (sub?)tree.
        } else {
            String currentKey = root.getCourseCode();
            BSTNode left = root.getLeftChild();
            BSTNode right = root.getRightChild();

            if (node.getCourseCode().compareTo(currentKey) < 0) { // left string "before" right string
                left = insert(left, node);
            } else if (node.getCourseCode().compareTo(currentKey) > 0) { // left string "after" right string
                right = insert(right, node);
            }

            root.setChildren(left, right);
            return root;
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

        return 1 + size(node.getLeftChild()) + size(node.getRightChild());
    }

    /**
     * find: Find a course given a course code
     */
    public BSTNode find(String courseCode) {
        return find(root, courseCode);
    }

    private BSTNode find(BSTNode node, String courseCode) {
        if (node == null) {
            return null;
        }

        int comparison = courseCode.compareTo(node.getCourseCode());
        if (comparison < 0) {
            return find(node.getLeftChild(), courseCode);
        } else if (comparison > 0) {
            return find(node.getRightChild(), courseCode);
        }

        return node;
    }

    public void remove(String courseCode) {
        root = remove(root, courseCode);
    }

    // Get the minimal node (left-most node).
    private BSTNode extractMin(BSTNode parent, BSTNode node) {
        if (node == null) {
            throw new NoSuchElementException("min: Trying to find minimum node of empty node.");
        }

        if (node.getLeftChild() == null) {
            parent.setChildren(node.getRightChild(), parent.getRightChild());
            return node;
        }

        return extractMin(node, node.getLeftChild());
    }

    private BSTNode remove(BSTNode node, String courseCode) {
        if (node == null) {
            throw new NoSuchElementException("remove: Trying to remove empty node.");
        }

        int comparison = courseCode.compareTo(node.getCourseCode());
        if (comparison < 0) {
            node.setChildren(remove(node.getLeftChild(), courseCode), node.getRightChild());
            return node;
        } else if (comparison > 0) {
            node.setChildren(node.getLeftChild(), remove(node.getRightChild(), courseCode));
            return node;
        } else {
            var left = node.getLeftChild();
            var right = node.getRightChild();
            if (left == null) {
                return right;
            }
            if (right == null) {
                return left;
            }

            BSTNode least = extractMin(node, right);
            if (least == right) {
                least.setChildren(left, right.getRightChild());
            } else {
                least.setChildren(left, right);
            }

            return least;
        }
    }

    @Override
    public Iterator<BSTNode> iterator() {
        return new BSTIterator(root);
    }

    private class BSTIterator implements Iterator<BSTNode> {
        Stack<BSTNode> current;

        public BSTIterator(BSTNode start) {
            if (start == null) {
                throw new IllegalArgumentException("initial node must be non-null");
            }

            current = new Stack<>();
            current.add(start);
        }
        @Override
        public boolean hasNext() {
            return !current.isEmpty();
        }

        @Override
        public BSTNode next() {
            var here = current.pop();
            if (here.left != null) current.push(here.left);
            if (here.right != null) current.push(here.right);
            return here;
        }
    }


    public static void main(String[] args)
    {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert("DA3018", "Datalogi", 7.5);
        tree.insert("MM2001", "Matematik I", 30);
        tree.insert("DA2004", "Programmeringsteknik", 7.5);
        tree.insert("DA4002", "Mjukvaruutveckling", 7.5);
        tree.insert("DA4004", "Introduktion till maskininlarning", 7.5);
        tree.insert("DA4005", "Algoritmer och komplexitet", 7.5);
        tree.insert("MM5016", "Numerisk analys I", 7.5);
        tree.insert("DA5001", "Numerisk analys II", 7.5);
        tree.insert("DA4003", "Programmeringsparadigm", 7.5);
        tree.insert("DA4001", "Databasteknik I", 7.5);
        int c = 0;
        for (var x: tree) {
            c++;
            System.out.format("%s - %s: %.01f\n", x.getCourseCode(), x.getCourseName(), x.getCredits());
        }

        System.out.format("Total: %d\n\nRemove DA3018 && DA4005\n\n", c);
        tree.remove("DA3018");
        tree.remove("DA4005");

        c = 0;
        for (var x: tree) {
            c++;
            System.out.format("%s - %s: %.01f\n", x.getCourseCode(), x.getCourseName(), x.getCredits());
        }
        System.out.format("Total: %d", c);

    }


    /**
     * Nodes in the search tree
     * This class should be sufficient for the project.
     */
    public class BSTNode {
        private String courseCode;
        private String courseName;
        private double credits;
        private BSTNode left = null;
        private BSTNode right = null;

        /**
         * Constructor
         */
        public BSTNode(String code, String name, double credits) {
            this.courseCode = code;
            this.courseName = name;
            this.credits = credits;
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

        public String getCourseCode() {
            return courseCode;
        }

        public String getCourseName() {
            return courseName;
        }

        public double getCredits() {
            return credits;
        }

        public BSTNode getLeftChild() {
            return left;
        }

        public BSTNode getRightChild() {
            return right;
        }
    }

}

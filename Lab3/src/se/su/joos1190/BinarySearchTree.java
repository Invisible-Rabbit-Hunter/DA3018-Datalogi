package se.su.joos1190;

import java.sql.Array;
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


    private BSTNode remove(BSTNode node, String courseCode) {
        if (node == null) {
            throw new NoSuchElementException("remove: Trying to remove non-existent node.");
        }

        int comparison = courseCode.compareTo(node.getCourseCode());
        if (comparison < 0) {
            node.setChildren(remove(node.getLeftChild(), courseCode), node.getRightChild());
            return node;
        } else if (comparison > 0) {
            node.setChildren(node.getLeftChild(), remove(node.getRightChild(), courseCode));
            return node;
        } else {
            if (node.getLeftChild() == null) {
                return node.getRightChild();
            }

            if (node.getRightChild() == null) {
                return node.getLeftChild();
            }

            BSTNode least = node.getRightChild();

            // If the first right child has no left child, simply place it where /node/ is, with the left child of /node/
            // as left child.
            if (least.getRightChild() == null) {
                least.setChildren(node.getLeftChild(), least.getRightChild());
            } else {
                // Otherwise, find the least element and replace it with node.
                BSTNode beforeLeast = node;
                while (least.getLeftChild() != null) {
                    beforeLeast = least;
                    least = least.getLeftChild();
                }

                beforeLeast.setChildren(least.getRightChild(), beforeLeast.getRightChild());
                least.setChildren(node.getLeftChild(), node.getRightChild());
            }

            return least;
        }
    }

    @Override
    public Iterator<BSTNode> iterator() {
        var current = new ArrayList<BSTNode>();
        current.add(root);

        return new Iterator<BSTNode>() {
            @Override
            public boolean hasNext() {
                return current.size() != 0;
            }

            @Override
            public BSTNode next() {
                if (current.isEmpty()) {
                    throw new NoSuchElementException();
                }

                var head = current.get(0);
                current.remove(0);
                if (head.left != null) current.add(head.left);
                if (head.right != null) current.add(head.right);

                return head;
            }
        };
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

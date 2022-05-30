import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
/**
 * Implementation of a BST.
 * @author Abdullah Ahmed
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException(" ");
        }
        size = 0;
        for (T everyData : data) {
            if (everyData == null) {
                throw new IllegalArgumentException(" ");
            }
            add(everyData);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException(" ");
        }
        if (root == null) {
            root = new BSTNode<>(data);
            size++;
        } else {
            add(data, root);
        }
    }
    /**
     * This is a recursive helper method for adding.
     *
     * @param data data to be added to new node
     * @param node current node
     */
    private void add(T data, BSTNode<T> node) {
        int checkSide = data.compareTo(node.getData());
        if (checkSide > 0) {
            if (node.getRight() == null) {
                node.setRight(new BSTNode<>(data));
                size++;
            } else {
                add(data, node.getRight());
            }
        }
        if (checkSide < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTNode<>(data));
                size++;
            } else {
                add(data, node.getLeft());
            }
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        BSTNode<T> dummyNode = new BSTNode<>(null);
        if (data == null) {
            throw new IllegalArgumentException(" ");
        } else {
            root = remove(root, dummyNode, data);
            size--;
            return dummyNode.getData();
        }
    }
    /**
     * This is a recursive helper method for removing.
     *
     * @param currentNode current node
     * @param data        Data to remove
     * @param dummyNode   dummyNode
     * @return node with data to be removed
     * @throws java.util.NoSuchElementException if the data is not found
     */
    private BSTNode<T> remove(BSTNode<T> currentNode, BSTNode<T> dummyNode, T data) {
        if (currentNode == null) {
            throw new NoSuchElementException();
        } else if (data.equals(currentNode.getData())) {
            dummyNode.setData(currentNode.getData());
            if (currentNode.getLeft() == null && currentNode.getRight() == null) {
                return null;
            }
            if (currentNode.getLeft() == null) {
                return currentNode.getRight();
            }
            if (currentNode.getRight() == null) {
                return currentNode.getLeft();
            }
            currentNode.setRight(successorNode(currentNode.getRight(), dummyNode));
            T newData = dummyNode.getData();
            dummyNode.setData(currentNode.getData());
            currentNode.setData(newData);
            return currentNode;
        } else if (data.compareTo(currentNode.getData()) < 0) {
            currentNode.setLeft(remove(currentNode.getLeft(), dummyNode, data));
            return currentNode;
        } else if (data.compareTo(currentNode.getData()) > 0) {
            currentNode.setRight(remove(currentNode.getRight(), dummyNode, data));
            return currentNode;
        }
        return currentNode;
    }
    /**
     * This is a recursive helper method for finding successor.
     *
     * @param currentNode current Node
     * @param dummyNode   dummy Node
     * @return data to be removed.
     */
    private BSTNode<T> successorNode(BSTNode<T> currentNode, BSTNode<T> dummyNode) {
        if (currentNode.getLeft() == null) {
            dummyNode.setData(currentNode.getData());
            return currentNode.getRight();
        } else {
            currentNode.setLeft(successorNode(currentNode.getLeft(), dummyNode));
        }
        return currentNode;
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException(" ");
        }
        if (root == null) {
            throw new java.util.NoSuchElementException(" ");
        }
        T foundData = get(data, root);
        if (foundData != null) {
            return foundData;
        } else {
            throw new java.util.NoSuchElementException(" ");
        }
    }
    /**
     * This is a recursive helper method for the function get();
     *
     * @param node     represents the node being checked
     * @param findData represents the data to be returned
     * @return the data in newNode
     */
    private T get(T findData, BSTNode<T> node) {
        int checkDirection = findData.compareTo(node.getData());
        if (checkDirection > 0) {
            if (node.getRight() == null) {
                return null;
            } else {
                return get(findData, node.getRight());
            }
        } else if (checkDirection < 0) {
            if (node.getLeft() == null) {
                return null;
            } else {
                return get(findData, node.getLeft());
            }
        } else {
            return node.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException(" ");
        }
        return contains(data, root) != null;
    }
    /**
     * This is a recursive helper method for contains.
     *
     * @param node the node being checked for the data we are looking for.
     * @param data the data getting searched for.
     * @return a node with the data we are searching for.
     */
    private BSTNode<T> contains(T data, BSTNode<T> node) {
        if (node == null) {
            return null;
        } else if (node.getData().equals(data)) {
            return node;
        } else if (data.compareTo(node.getData()) < 0) {
            return contains(data, node.getLeft());
        } else {
            return contains(data, node.getRight());
        }
    }
    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> outputList = new ArrayList<>();
        preorder(root, outputList);
        return outputList;
    }
    /**
     * This is a recursive helper method for preorder.
     *
     * @param dummyNode  the node being checked
     * @param outputList the list that data gets added to
     */
    private void preorder(BSTNode<T> dummyNode, List<T> outputList) {
        if (dummyNode != null) {
            outputList.add(dummyNode.getData());
            preorder(dummyNode.getLeft(), outputList);
            preorder(dummyNode.getRight(), outputList);
        }
    }
    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        return inorder(root, new ArrayList<>());
    }


    /**
     * This is a recursive helper method for inorder.
     *
     * @param dummyNode  the node being checked
     * @param outputList the list that data gets added to
     * @return fresh
     */
    private List<T> inorder(BSTNode<T> dummyNode, List<T> outputList) {
        if (dummyNode == null) {
            return outputList;
        } else {
            inorder(dummyNode.getLeft(), outputList);
            outputList.add(dummyNode.getData());
            inorder(dummyNode.getRight(), outputList);
        }
        return outputList;
    }
    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> outputList = new ArrayList<>();
        if (root != null) {
            postorder(root, outputList);
        }
        return outputList;
    }
    /**
     * This is a recursive helper method for postorder.
     *
     * @param dummyNode  the node being checked
     * @param outputList the list that data gets added to
     */
    private void postorder(BSTNode<T> dummyNode, List<T> outputList) {
        if (dummyNode.getLeft() != null) {
            postorder(dummyNode.getLeft(), outputList);
        }
        if (dummyNode.getRight() != null) {
            postorder(dummyNode.getRight(), outputList);
        }
        outputList.add(dummyNode.getData());
    }
    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        ArrayList<T> list = new ArrayList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
            levelorder(list, queue);
        }
        return list;
    }
    /**
     * This is a recursive helper method for levelorder.
     *
     * @param list  the node being checked
     * @param queue the list that data gets added to
     */
    private void levelorder(List<T> list, Queue<BSTNode<T>> queue) {
        BSTNode<T> temp = queue.poll();
        if (temp != null) {
            list.add(temp.getData());
            if (temp.getLeft() != null) {
                queue.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                queue.add(temp.getRight());
            }
            levelorder(list, queue);
        }
    }
    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }
    /**
     * This is a recursive helper method for height.
     *
     * @param root represents the root of the tree
     * @return the height of the tree
     */
    private int height(BSTNode<T> root) {
        if (root == null) {
            return -1;
        } else {
            int leftSize = height(root.getLeft());
            int rightSize = height(root.getRight());
            if (leftSize > rightSize) {
                return 1 + leftSize;
            } else {
                return 1 + rightSize;
            }
        }
    }
    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
     public List<T> kLargest(int k) {
         if (k > size) {
             throw new IllegalArgumentException("The value of k inputted is larger than the size of the BST.");
         }
         List<T> sortedList = inorder();
         List<T> ret = new ArrayList<>();
         for (int i = size - k; i < size; i++) {
             ret.add(sortedList.get(i));
         }
         return ret;    
     }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}

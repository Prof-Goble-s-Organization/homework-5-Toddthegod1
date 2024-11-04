package hw5;

import java.util.NoSuchElementException;

/**
 * Linked implementation of a binary search tree. The binary search tree
 * inherits the methods from the binary tree. The add and remove methods must
 * must be overridden so that they maintain the BST Property. The contains, get
 * and set methods are overridden to improve their performance by taking
 * advantage of the BST property. The inherited size and traversal methods work
 * well as they are.
 * 
 * @author William Goble
 * @author Dickinson College
 *
 */
public class COMP232LinkedBinarySearchTree<K extends Comparable<K>, V> extends COMP232LinkedBinaryTree<K, V> {

	/*
	 * NOTE: We inherit the size and root fields, and the BTNode class from the
	 * LinkedBinaryTree class because they were declared as protected in that
	 * class.
	 */

	/**
	 * Construct an empty binary search tree.
	 */
	public COMP232LinkedBinarySearchTree() {
		super();
	}

	/**
	 * Construct a binary search tree with a single node at the root.
	 * 
	 * @param key
	 *            the key for the root.
	 * @param value
	 *            the value for the root.
	 */
	public COMP232LinkedBinarySearchTree(K key, V value) {
		super(key, value);
	}

	/**
	 * Construct a binary search tree using the provided keys and values. The
	 * key,value pairs are inserted into the tree in level order. If the
	 * resulting tree does not satisfy the BST Property, then an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param keys
	 *            the keys.
	 * @param values
	 *            the values.
	 * @throws IllegalArgumentException
	 *             if the keys and values do not have the same length, the keys
	 *             or the values are empty, or the keys are not specified in an
	 *             order that results in a valid binary search tree.
	 */
	public COMP232LinkedBinarySearchTree(K[] keys, V[] values) {
		super(keys, values);

		if (!checkBSTProperty()) {
			throw new IllegalArgumentException(
					"Key, Value pairs did not satisfy BST property.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean contains(K key) {
		return containsHelper(root, key);
	}

	/*
	 * Recursive helper method that checks if the key can be found in the
	 * subtree rooted at subTreeRoot.
	 */
	private boolean containsHelper(BTNode<K, V> subTreeRoot, K key) {
		if (subTreeRoot == null) {
			return false; // off the tree.
		} else if (subTreeRoot.key.equals(key)) {
			return true; // found it.
		} else if (key.compareTo(subTreeRoot.key) < 0) {
			/*
			 * The key we are looking for is less than the key at the
			 * subTreeRoot so if it is in the tree it will be in the left
			 * subtree.
			 */
			return containsHelper(subTreeRoot.left, key);
		} else {
			/*
			 * The key we are looking for is greater than or equal to the key at
			 * the subTreeRoot so if it is in the tree it will be in the right
			 * subtree.
			 */
			return containsHelper(subTreeRoot.right, key);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public V get(K key) {
		// Intentionally not implemented - see homework assignment.
		return getHelper(root, key);
		
	}

	private V getHelper(BTNode<K, V> subTreeRoot, K key) {
		if (subTreeRoot == null) {
			return null;
		}
		else if (subTreeRoot.key.equals(key)) {
			return subTreeRoot.value;
		}
		else if (key.compareTo(subTreeRoot.key) < 0) {
			return getHelper(subTreeRoot.left, key);
		}
		else {
			return getHelper(subTreeRoot.right, key);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void set(K key, V value) {
		// Intentionally not implemented - see homework assignment.
		BTNode<K,V> node = findNode(root, key);
		if (node != null) {
			node.value = value;
		} else {
			throw new NoSuchElementException();
		}
	}

	private BTNode<K,V> findNode(BTNode<K, V> subTreeRoot, K key) {
		if (subTreeRoot == null) {
			return null;
		}
		else if (subTreeRoot.key.equals(key)) {
			return subTreeRoot;
		}
		else if (key.compareTo(subTreeRoot.key) < 0) {
			return findNode(subTreeRoot.left, key);
		}
		else {
			return findNode(subTreeRoot.right, key);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(K key, V value) {
		BTNode<K, V> nodeToAdd = new BTNode<K, V>(key, value);

		if (size == 0) { // tree is empty!
			root = nodeToAdd;
		} else {
			addNodeToSubTree(root, nodeToAdd);
		}
		size++;
	}

	/*
	 * Add the nodeToAdd to the subtree rooted at subTreeRoot.
	 */
	private void addNodeToSubTree(BTNode<K, V> subTreeRoot,
			BTNode<K, V> nodeToAdd) {
		if (nodeToAdd.key.compareTo(subTreeRoot.key) < 0) {
			/*
			 * Key of the new node is less than the key at subTreeRoot so we are
			 * going to add the new node into the left sub tree.
			 */
			if (subTreeRoot.left == null) {
				/*
				 * The left subtree is empty, so make the new node the left
				 * child of the subtree root.
				 */
				subTreeRoot.left = nodeToAdd;
				nodeToAdd.parent = subTreeRoot;
			} else {
				/*
				 * The left subtree is not empty, so add the new node to that
				 * sub tree.
				 */
				addNodeToSubTree(subTreeRoot.left, nodeToAdd);
			}
		} else {
			/*
			 * The key of the new node is greater than or equal to the key at
			 * the subTreeRoot so we are going to add the new node to the right
			 * sub tree. This is exactly the complement of the approach used
			 * above.
			 */
			if (subTreeRoot.right == null) {
				subTreeRoot.right = nodeToAdd;
				nodeToAdd.parent = subTreeRoot;
			} else {
				addNodeToSubTree(subTreeRoot.right, nodeToAdd);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public V remove(K key) {
		// Intentionally not implemented - see homework assignment.
		return removeHelper(root, key);
	}

	private V removeHelper(BTNode<K,V> subTreeRoot, K key) {
		if (subTreeRoot == null) {
			return null;
		}

		int cmp = key.compareTo(subTreeRoot.key);
		if (cmp < 0) {
			return removeHelper(subTreeRoot.left, key);
		}
	    else if (cmp > 0) {
			return removeHelper(subTreeRoot.right, key);
			}
		else {
			BTNode<K,V> removed = subTreeRoot;
			V removedVal = removed.value;
			int tempSize = size - 1;
			size--;
			// Node has no children
			if (subTreeRoot.left == null && subTreeRoot.right == null) {
				if (subTreeRoot == root){
					root = null;
				} else if (subTreeRoot.parent.left == subTreeRoot){
					subTreeRoot.parent.left = null;
				} else {
					subTreeRoot.parent.right = null;
				}
			}
			// Node has one child
			else if (subTreeRoot.left == null) {
				if (subTreeRoot == root){
					root = subTreeRoot.right;
				} else if (subTreeRoot.parent.left == subTreeRoot){
					subTreeRoot.parent.left = subTreeRoot.right;
					subTreeRoot.right.parent = subTreeRoot.parent;
				} else {
					subTreeRoot.parent.right = subTreeRoot.right;
					subTreeRoot.right.parent = subTreeRoot.parent;
				}
			} else if (subTreeRoot.right == null) {
				if (subTreeRoot == root){
					root = subTreeRoot.left;
				} else if (subTreeRoot.parent.left == subTreeRoot){
					subTreeRoot.parent.left = subTreeRoot.left;
					subTreeRoot.left.parent = subTreeRoot.parent;
				} else {
					subTreeRoot.parent.right = subTreeRoot.left;
					subTreeRoot.left.parent = subTreeRoot.parent;
				}
			} else {
				// Node has two children
				BTNode<K,V> successor = findMin(subTreeRoot.right);
				subTreeRoot.key = successor.key;
				subTreeRoot.value = successor.value;
				// successor.parent.right = null;
				// successor.parent.left = subTreeRoot.left;
				removeHelper(subTreeRoot.right, successor.key);
			}
			size = tempSize;
			return removedVal;
		}
	}

	
	private BTNode<K, V> findMin(BTNode<K, V> node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	/*
	 * Helper method that verifies the BST property of this tree by traversing
	 * the tree and verifying that at each node the key of the left child is <
	 * the key of the node and that the key of the right child is >= the key of
	 * the node.  This is used by the 
	 */
	boolean checkBSTProperty() {
		return checkBSTPropertyHelper(root);
	}

	private boolean checkBSTPropertyHelper(BTNode<K, V> subTreeRoot) {
		if (subTreeRoot == null) {
			return true; // off tree.
		} else {
			if (leftChildOK(subTreeRoot) && rightChildOK(subTreeRoot)) {
				return checkBSTPropertyHelper(subTreeRoot.left)
						&& checkBSTPropertyHelper(subTreeRoot.right);
			} else {
				return false;
			}
		}
	}

	private boolean leftChildOK(BTNode<K, V> node) {
		if (node.left == null) {
			return true;
		} else {
			// true if key at node is > key at left child.
			return node.key.compareTo(node.left.key) > 0;
		}
	}

	private boolean rightChildOK(BTNode<K, V> node) {
		if (node.right == null) {
			return true;
		} else {
			// true if key at node is <= key at right child.
			return node.key.compareTo(node.right.key) <= 0;
		}
	}
}

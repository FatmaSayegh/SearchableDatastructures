package fatma.project;

import java.util.Stack;

/**
 * 
 * In a binary search tree, the left node is less than the parent node The right
 * node is greater than the parent node
 * 
 * @author Fatma Al Sayegh
 *
 * @param <Item>
 */
public class BinarySearchSet<Item extends Comparable<Item>> {
	private Node<Item> root;

	private static class Node<Item extends Comparable<Item>> {
		private Item key;
		private Node<Item> p; // parent
		private Node<Item> left;
		private Node<Item> right;

	}

	/*
	 * Main operations
	 */
	/**
	 * Adds an item to the set
	 * 
	 * @param item
	 */
	public void add(Item item) {

		// if its empty, ie no root, set the root
		if (setEmpty()) {
			root = getNewNode(item);
			return;
		}
		// parent is the parent node to which this item will be added too
		Node<Item> parent = null;
		Node<Item> current = root;
		while (current != null) {
			// DISTINCT
			if (current.key == item || current.key.compareTo(item) == 0) {
				return;
			}

			// parent is the current node because the current then changes to left or right
			// which could be null
			parent = current;
			// if the item is less than the current
			if (item.compareTo(current.key) < 0) {
				current = current.left;
			} else { // else its greater than because distinct
				current = current.right;
			}
		}
		// current is not useful anymore
		current = getNewNode(item);
		// if item is less than current
		if (item.compareTo(parent.key) < 0) {
			parent.left = current;
		} else {
			parent.right = current;
		}
		// set the parent
		current.p = parent;

	}
	
	

	/**
	 * removes an item from the set
	 * 
	 * @param item
	 */
	public void remove(Item item) {

		Node<Item> current = getNodeFromItem(item);
		// item not in set
		if (current == null)
			return;

		/*
		 * In cases where it has at most one child, its easy just replace it with the
		 * right or left child it has
		 */
		if (current.left == null) { // if it has no left child
			transplant(current, current.right);
		} else if (current.right == null) { // if it has no right child
			transplant(current, current.left);
		} else {
			// in this case, it has 2 children

			/*
			 * find the minimum one to the right, which is the minimum one greater than the
			 * current
			 */
			Node<Item> successor = minimum(current.right);

			/*
			 * If the successor's parent is the current this means that the successor is on
			 * the right side
			 * 
			 * This is because the right side keeps getting greater, and we start searching
			 * to the right
			 */
			if (successor.p == current) {
				// put successor in place of the current
				transplant(current, successor);

				/*
				 * since the node is to the right and is a direct child, we don't need to change
				 * anything on the right side
				 */

				// we just have to modify the left
				successor.left = current.left;
				successor.left.p = successor;

				return;
			}

			// if its not a direct child, then it is not the right child

			/*
			 * The successor element has no left children because its the minumum so lets
			 * remove it by putting whatever is to its right in its place
			 */
			transplant(successor, successor.right);
			// it has no left so fix the rights
			successor.right = current.right;
			successor.right.p = successor;

		}

	}

	/**
	 * Transplants the node <b>v</b> in place of the node <b>u</b> mainly works if
	 * the v is a child of u
	 * 
	 * @param u
	 * @param v
	 */
	private void transplant(Node<Item> u, Node<Item> v) {
		if (u.p == null) {
			root = v;
			return;
		}

		if (u == u.p.left) {
			u.p.left = v;
		} else {
			u.p.right = v;
		}
		if (v != null)
			v.p = u.p;

	}

	/**
	 * Minimum is a helper function for deletion, it finds the minimum node in the
	 * subtree
	 * 
	 * @param u
	 * @return
	 */
	private Node<Item> minimum(Node<Item> u) {
		while (u.left != null)
			u = u.left;
		return u;
	}

	/**
	 * Check if an item is in the set
	 * 
	 * @param item
	 * @return If the item is in the set
	 */
	public boolean isElement(Item item) {
		// if the node gotten from this item isn't null, it exists
		return getNodeFromItem(item) != null;
	}

	/**
	 * Checks whether the set is empty
	 * 
	 * @return If the set is empty
	 */
	public boolean setEmpty() {
		// set is empty if it has no head
		return root == null || root.key == null;
	}

	/**
	 * Checks the set size
	 * 
	 * @return the size of the set
	 */
	public int setSize() {
		return size(root);
	}

	private int size(Node<Item> item) {
		if (item == null || item.key == null)
			return 0;
		int size = 1;
		size += size(item.left);
		size += size(item.right);
		return size;
	}
	
	/**
	 * Gets the tree maximum height
	 * @return
	 */
	public int treeHeight() {
		return this.branchDepth(this.root);
	}
	
	private int branchDepth(Node<Item> node) {
		if (node == null) {
			return 0;
		}
		int size = 1;
		
		int leftSize = branchDepth(node.left);
		int rightSize = branchDepth(node.right);
		if (leftSize > rightSize) {
			size += leftSize;
		}else {
			size += rightSize;
		}
		return size;
	}

	/**
	 * Find the node in the set for this item
	 * 
	 * @param item
	 * @return
	 */
	private Node<Item> getNodeFromItem(Item item) {
		Node<Item> current = root;
		while (current != null) {
			if (item.compareTo(current.key) == 0)
				return current;
			// if the item is less than the current
			if (item.compareTo(current.key) < 0) {
				current = current.left;
			} else {
				current = current.right;
			}
		}

		return current;
	}

	/**
	 * Get a node with only a key value from an item
	 * 
	 * @param item
	 * @return
	 */
	private Node<Item> getNewNode(Item item) {
		Node<Item> node = new Node<>();
		node.key = item;
		return node;
	}

	/*
	 * Set Theoretical operations
	 */

	/**
	 * Returns a new set with the unions of 2 sets the items are referenced and the
	 * nodes are recreated
	 * 
	 * @param<Item>
	 * @param a
	 * @param b
	 * @return
	 */
	public BinarySearchSet<Item> union(BinarySearchSet<Item> b) {
		// create the union set
		BinarySearchSet<Item> union = new BinarySearchSet<>();
		// find the smaller tree first

		this.loop(new Runner<Item>() {

			@Override
			public void runOn(Node<Item> item) {
				union.add(item.key);

			}

		});

		b.loop(new Runner<Item>() {

			@Override
			public void runOn(Node<Item> item) {
				union.add(item.key);

			}

		});

		return union;

	}

	/**
	 * Finds the intersection between two doubly sets
	 * 
	 * @param<Item>
	 * @param a The First set
	 * @param b The Second set
	 * @return The intersection set
	 */
	public BinarySearchSet<Item> intersect(BinarySearchSet<Item> b) {
		BinarySearchSet<Item> intersection = new BinarySearchSet<>();

		this.loop(new Runner<Item>() {

			@Override
			public void runOn(Node<Item> item) {
				if (b.isElement(item.key)) {
					intersection.add(item.key);
				}

			}

		});

		return intersection;
	}

	/**
	 * Finds the items in set <b>this</b> that are not in set <b>b</b>
	 * 
	 * @param <Item>
	 * @param a      The first set
	 * @param b      The second set
	 * @return The difference set
	 */
	public BinarySearchSet<Item> difference(BinarySearchSet<Item> b) {
		BinarySearchSet<Item> diff = new BinarySearchSet<Item>();

		this.loop(new Runner<Item>() {

			@Override
			public void runOn(Node<Item> item) {
				if (!b.isElement(item.key)) {
					diff.add(item.key);
				}

			}

		});

		return diff;
	}

	/**
	 * Checks if the set<b>this</b> is a subset of <b>b</b>
	 * 
	 * @param <Item>
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean subset(BinarySearchSet<Item> b) {

		Mutable<Boolean> val = new Mutable<>(true);
		this.loop(new Runner<Item>() {

			@Override
			public void runOn(Node<Item> item) {
				if (!b.isElement(item.key)) {
					val.t = false;
				}

			}

		});
		return val.t;
	}

	/*
	 * 
	 * Helper methods to loop over all elements of a search tree
	 * 
	 * 
	 */

	/**
	 * Runs the runner#runOn on each element of the binary search tree
	 * 
	 * @param runner
	 */
	private void loop(Runner<Item> runner) {
		// create a stack to add all nodes to it
		Stack<Node<Item>> stack = new Stack<Node<Item>>();
		// add the root
		stack.add(this.root);
		// loop through stack
		while (!stack.isEmpty()) {
			Node<Item> current = stack.pop();
			if (current == null)
				continue;
			runner.runOn(current);

			// add the left and rights to stack
			if (current.left != null)
				stack.add(current.left);
			if (current.right != null)
				stack.add(current.right);
		}
	}

	private static interface Runner<Item extends Comparable<Item>> {

		public void runOn(Node<Item> item);

	}

	// so that we can modify values inside the Runner loop
	// used in subset
	private static class Mutable<T> {
		private T t;

		public Mutable(T t) {
			this.t = t;
		}
	}
	
	
}

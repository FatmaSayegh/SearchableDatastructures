package fatma.project;

/**
 * 
 * SETS AS doubly linked lists
 * 
 * @author Fatma Al Sayegh
 *
 *
 * @param <Item>
 */
public class DoublySet<Item extends Comparable<Item>> {
	private Node<Item> head;

	private static class Node<Item extends Comparable<Item>> {
		private Item key;
		private Node<Item> next;
		private Node<Item> prev;
	}

	public DoublySet() {
		head = null;
	}
	
	public DoublySet(Node<Item> h) {
		head = h;
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
		// if there isn't head, make the item the head
		if (head == null) {
			head = getNewNode(item);
			return;
		}
		Node<Item> last = head;
		while (last.next != null) {
			// DISTINCT
			if (last.key == item || last.key.compareTo(item) == 0)
				return;

			last = last.next;
		}
		// get new node
		Node<Item> n = getNewNode(item);
		// set the previous to the current last
		n.prev = last;
		// set the current last's next to the new node
		last.next = n;
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
		// set the previous next to the current next
		current.prev.next = current.next;
		// set the current next's previous to the current previous
		current.next.prev = current.prev;
		// this way current is unused anymore and left for garbage collection

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
		return head == null || head.key == null;
	}

	/**
	 * Checks the set size
	 * 
	 * @return the size of the set
	 */
	public int setSize() {
		int size = 0;
		for (Node<Item> current = head; current != null; current = current.next) {
			size++;
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
		Node<Item> current = head;
		/*
		 * If the current element isn't null and its not the item find the next one and
		 * repeat
		 */
		while (current != null && !current.key.equals(item)) {
			current = current.next;
		}
		// the current in this case is either null or the item's node
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
	public DoublySet<Item> union(DoublySet<Item> b) {
		// create the union set
		DoublySet<Item> union = new DoublySet<>();

		/*
		 * Start from the set a's head and add all elements of a move onto set b's head
		 * and add all elements of b
		 * 
		 * if any of the sets is empty, while loop won't run cuz current would be null
		 */
		Node<Item> current = this.head;
		while (current != null) {
			union.add(current.key);
			current = current.next;
		}

		current = b.head;
		while (current != null) {
			union.add(current.key);
			current = current.next;
		}

		return union;

	}

	/**
	 * Finds the intersection between two doubly sets
	 * 
	 * @param a The First set
	 * @param b The Second set
	 * @return The intersection set
	 */
	public DoublySet<Item> intersect(DoublySet<Item> b) {
		Node<Item> current = null;

		for (Node<Item> item = this.head; item != null; item = item.next) {
			if (b.isElement(item.key)) {
				if (current == null) {
					current = this.getNewNode(item.key);
					continue;
				}
				current.next = this.getNewNode(item.key);
				current.next.prev = current;
				current = current.next;
			}
		}
		return new DoublySet<Item>(current);
	}

	/**
	 * Finds the items in set <b>a</b> that are not in set <b>b</b>
	 * 
	 * @param <Item>
	 * @param a      The first set
	 * @param b      The second set
	 * @return The difference set
	 */
	public DoublySet<Item> difference(DoublySet<Item> b) {
		
		Node<Item> current = null;

		for (Node<Item> item = this.head; item != null; item = item.next) {
			if (!b.isElement(item.key)) {
				if (current == null) {
					current = this.getNewNode(item.key);
					continue;
				}
				current.next = this.getNewNode(item.key);
				current.next.prev = current;
				current = current.next;
			}
		}
		return new DoublySet<Item>(current);
	}

	/**
	 * Checks if the set<b>a</b> is a subset of <b>b</b>
	 * 
	 * @param <Item>
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean subset(DoublySet<Item> b) {
		for (Node<Item> item = this.head; item != null; item = item.next) {
			// if any element of a is not in b, return false
			if (!b.isElement(item.key))
				return false;
		}
		// didn't return false so all elements in a are in b, so return true
		return true;
	}

}
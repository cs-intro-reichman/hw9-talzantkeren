/**
 * Represents a list of Nodes. 
 */
public class LinkedList {
	
	private Node first; // pointer to the first element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		first = null;
		last = first;
		size = 0;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}
		Node current = first;
		for(int i = 0; i < index; i++){
			current=current.next;}
		return current;
	}
	public int getSize(){
		return size;
	}
	public Node getFirst()
	{
		return first;
	}
	public Node getLast()
	{
		return last;
	}
	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given 
	 * index is either 0 or the list's size, the addition time is O(1). 
	 * 
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}
		Node add = new Node(block);
		if (index == 0) {
			add.next = first;
			first = add;
			if (size == 0) {
				last = add;
			}
		} else if (index == size) {
			last.next = add;
			last = add;
		} else {
			Node current = first;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			add.next = current.next;
			current.next = add;
		}
		size++;
	}
	

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		Node add = new Node(block);
		if (size == 0) {
			first = add; 
			last = add;  
		} 
		else {
			last.next = add; 
			last = add;      
		}
		size++;
	}
	
	/**
	 * Creates a new node that points to the given memory block, and adds it 
	 * to the beginning of this list (the node will become the list's first element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		Node add = new Node(block);
		if (size == 0) {
			first = add; 
			last = add;  
		}
		else{
			add.next=first;
			first = add;
		}
		size++;
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		if(index < 0 || index >= size ){
			throw new IllegalArgumentException("index must be between 0 and size");
		}
		Node current=first;
		for( int i =0; i < index; i++){
			current=current.next;
		}
		return current.block;
	}	

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		Node current = first;
		int c = 0;
		while (current != null) {
			if (current.block.equals(block)) {
				return c;
			}
			c++;
			current = current.next;
		}
		return -1;
	}
	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {
		if (node == null) throw new IllegalArgumentException(" NullPointerException!");
		if (first == null) throw new IllegalArgumentException("list is empty");

		if (first.block.equals(node.block)) {
			first = first.next;
			if (first == null) last = null;
			size--;
			return;
		}

		Node current = first;
		while (current.next != null && !current.next.block.equals(node.block)) {
			current = current.next;
		}
		
		if (current.next == null) {
			throw new IllegalArgumentException("node doesn't exist");
		}

		current.next = current.next.next;
		if (current.next == null) last = current;
		size--;
	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("index is illegal");
		}

		if (index == 0) {
			first = first.next;
			if (first == null) last = null;
			size--;
			return;
		}

		Node current = first;
		for (int i = 0; i < index - 1; i++) {
			current = current.next;
		}

		current.next = current.next.next;
		if (current.next == null) last = current;
		size--;
	}
	
	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		if (block == null) throw new IllegalArgumentException("index must be between 0 and size");
		if (first == null) throw new IllegalArgumentException("list is empty");

		if (first.block.equals(block)) {
			first = first.next;
			if (first == null) last = null;
			size--;
			return;
		}

		Node current = first;
		while (current.next != null && !current.next.block.equals(block)) {
			current = current.next;
		}

		if (current.next == null) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}

		current.next = current.next.next;
		if (current.next == null) last = current;
		size--;
	}

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public void sort() {
		for (int i = 0; i < size - 1; i++) {

			for (int j = 0; j < size - i - 1; j++) {

				Node current = getNode(j);
				Node next = current.next;

				if (current.block.baseAddress > next.block.baseAddress) {
					MemoryBlock temp = current.block;
					current.block = next.block;
					next.block = temp;
				}
			}
		}
	}
	public ListIterator iterator(){
		return new ListIterator(first);
	}
	
	/**
	 * A textual representation of this list, for debugging.
	 */
	public String toString() {
		//// Replace the following statement with your code
		return "";
	}
}

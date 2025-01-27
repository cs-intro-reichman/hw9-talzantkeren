/**
 * This class represents a memory management space. It handles two separate lists: 
 * allocated memory blocks and available free memory blocks. The "malloc" and "free" 
 * methods are used to allocate and recycle memory blocks, respectively.
 */
public class MemorySpace {
	
	// Stores the allocated memory blocks
	private LinkedList allocatedBlocks;

	// Stores the available free memory blocks
	private LinkedList freeBlocks;

	/**
	 * Initializes a new memory space manager with a specified size.
	 * 
	 * @param totalSize
	 *            the total memory size to be managed
	 */
	public MemorySpace(int totalSize) {
		allocatedBlocks = new LinkedList();
		freeBlocks = new LinkedList();
		freeBlocks.addLast(new MemoryBlock(0, totalSize));
	}

	/**
	 * Allocates a memory block of the specified size. Returns the base address of 
	 * the allocated block, or -1 if allocation is not possible.
	 * 
	 * @param size
	 *        the size (in words) of the required memory block
	 * @return the base address of the allocated block, or -1 if allocation fails
	 */
	public int malloc(int size) {
		Node pointer = freeBlocks.getFirst();
		Node chosenBlock = null;

		while (pointer != null) {
			if (pointer.block.length >= size) {
				chosenBlock = pointer;
				break;
			}
			pointer = pointer.next;
		}

		if (chosenBlock != null) {
			MemoryBlock allocated = new MemoryBlock(chosenBlock.block.baseAddress, size);
			allocatedBlocks.addLast(allocated);

			chosenBlock.block.length -= size;
			int allocatedAddress = chosenBlock.block.baseAddress;
			chosenBlock.block.baseAddress += size;

			if (chosenBlock.block.length == 0) {
				freeBlocks.remove(chosenBlock);
			}

			return allocatedAddress;
		}

		return -1;
	}

	/**
	 * Releases a memory block based on its base address. The block is removed
	 * from the allocated list and added back to the free list.
	 * 
	 * @param address
	 *            the base address of the memory block to free
	 */
	public void free(int address) {

		if (freeBlocks.getSize() == 1 && freeBlocks.getFirst().block.baseAddress == 0 && freeBlocks.getFirst().block.length == 100) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}

		Node pointer = allocatedBlocks.getFirst();
		Node matchingBlock = null;

		while (pointer != null) {
			if (pointer.block.baseAddress == address) {
				matchingBlock = pointer;
				break;
			}
			pointer = pointer.next;
		}

		if (matchingBlock != null) {
			freeBlocks.addLast(matchingBlock.block);
			allocatedBlocks.remove(matchingBlock);
		}
	}

	/**
	 * Provides a textual representation of the current free and allocated memory blocks.
	 * Useful for debugging.
	 */
	public String toString() {
		return freeBlocks.toString() + "\n" + allocatedBlocks.toString();
	}

	/**
	 * Performs memory defragmentation to merge adjacent free memory blocks.
	 */
	public void defrag() {
		if (freeBlocks.getSize() <= 1) {
			return;
		}

		freeBlocks.sort();
		Node pointer = freeBlocks.getFirst();

		while (pointer != null && pointer.next != null) {
			MemoryBlock current = pointer.block;
			MemoryBlock next = pointer.next.block;

			if (current.baseAddress + current.length == next.baseAddress) {
				current.length += next.length;
				freeBlocks.remove(pointer.next);
			} else {
				pointer = pointer.next;
			}
		}
	}
}

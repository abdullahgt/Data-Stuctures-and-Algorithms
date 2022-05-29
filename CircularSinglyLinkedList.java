import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Abdullah Ahmed
 * @version 1.0
 * @userid YOUR USER ID HERE (i.e. gburdell3)
 * @GTID 903584611
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class CircularSinglyLinkedList<T> {


    // Do not add new instance variables or modify existing ones.
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the linked list.");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index has to be between zero and the size of list.");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            CircularSinglyLinkedListNode<T> node = new CircularSinglyLinkedListNode<>(data);
            CircularSinglyLinkedListNode<T> temp = head;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }
            node.setNext(temp.getNext());
            temp.setNext(node);
            size++;
        }
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the linked list.");
        }
        CircularSinglyLinkedListNode<T> node = new CircularSinglyLinkedListNode<>(data);
        if (size == 0) {
            head = node;
            head.setNext(head);
        } else {
            CircularSinglyLinkedListNode<T> temp = new CircularSinglyLinkedListNode<>(head.getData(), head.getNext());
            head.setData(data);
            head.setNext(temp);
        }
        size++;
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the linked list.");
        }
        if (size == 0) {
            addToFront(data);
        } else {
            CircularSinglyLinkedListNode<T> temp = new CircularSinglyLinkedListNode<>(head.getData(), head.getNext());
            head.setData(data);
            head.setNext(temp);
            head = head.getNext();
            size++;
        }
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index has to be between zero and the size of the list.");
        }

        if (index == 0) {
            return removeFromFront();
        } else {
            CircularSinglyLinkedListNode<T> temp = head;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }
            CircularSinglyLinkedListNode<T> node = temp.getNext();
            temp.setNext(node.getNext());
            size--;
            return (T) node.getData();
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty.");
        }
        T returnData = head.getData();
        if (size == 1) {
            head = null;
        } else {
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
        }
        size--;
        return returnData;
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty.");
        }
        if (size == 1) {
            return removeFromFront();
        } else {
            CircularSinglyLinkedListNode<T> temp = head;
            for (int i = 0; i < size - 2; i++) {
                temp = temp.getNext();
            }
            CircularSinglyLinkedListNode<T> node = temp.getNext();
            temp.setNext(head);
            node.setNext(null);
            size--;
            return (T) node.getData();
        }
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index has to be between zero and the size of the list.");
        }
        if (index == 0) {
            return head.getData();
        } else {
            CircularSinglyLinkedListNode<T> temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            return (T) temp.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the linked list.");
        }
        if (size == 0) {
            throw new NoSuchElementException("The data is not found in the list as list is empty.");
        }
        CircularSinglyLinkedListNode<T> node = null;
        CircularSinglyLinkedListNode<T> previous = null;
        CircularSinglyLinkedListNode<T> temp = head;
        if (head.getData().equals(data)) {
            node = head;
        }
        for (int i = 0; i < size - 1; i++) {
            if (temp.getNext().getData().equals(data)) {
                previous = temp;
                node = temp.getNext();
                System.out.println(node.getData());

            }
            temp = temp.getNext();
        }
        if (node == null) {
            throw new NoSuchElementException("This data does not exist in the list.");
        } else {
            if (node.equals(head)) {
                return removeFromFront();
            } else {
                previous.setNext(node.getNext());
                size--;
                return node.getData();
            }
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] a = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> temp = head;
        for (int i = 0; i < size; i++) {
            a[i] = temp.getData();
            temp = temp.getNext();
        }
        return a;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}

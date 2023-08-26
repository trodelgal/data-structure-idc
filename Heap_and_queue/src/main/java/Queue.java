import java.time.LocalTime;

/**
 * The Queue class implement a FIFO queue using a node-based doubly linked list.
 *
 * This class used for implement queue in my system.
 * The manager will use this class to get the next patient by creation time.
 */
public class Queue <T> {
    private Node<T> first;
    private Node<T> last;
    private int length;

    /**
     * A standard constructor for the Queue class
     */
    public Queue() {
        this.first = null;
        this.last = null;
        this.length = 0;
    };


    /**
     * Add method - add new T to the end of the queue.
     */
    public void add(T t) {
        Node newNode = new Node<>(t);
        if (length == 0) {
            first = newNode;
            last = newNode;
        } else {
            newNode.setPrev(last);
            last.setNext(newNode);
            last = newNode;
        }
        length++;
    };

    /**
     * Get method - return the next T in the queue and remove it.
     *
     * @return T - the next T in the queue.
     */
    public T get() {
        if (first == null) {
            return null;
        }
        T firstPatient = first.getPatient();
        this.remove(firstPatient);
        return firstPatient;
    };

    /**
     * Remove method - remove the input element from the queue
     *
     * @param t - T object to be remove.
     */
    public void remove(T t) {
        Node currentNode = first;
        for (int i = 1; i <= this.length; i++) {
            if (currentNode.getPatient().equals(t)) {
                if (i == this.length && i == 1) {
                    this.first = null;
                    this.last = null;
                } else if (i == this.length) {
                    currentNode.getPrev().setNext(null);
                    this.last = currentNode.getPrev();
                } else if (i == 1) {
                    currentNode.getNext().setPrev(null);
                    first = currentNode.getNext();
                } else {
                    currentNode.getPrev().setNext( currentNode.getNext());
                    currentNode.getNext().setPrev(currentNode.getPrev());
                }
                ;
                this.length--;
                return;
            }
            ;
            currentNode = currentNode.getNext();
        };

    };

    public int getLength() {
        return this.length;
    };

}


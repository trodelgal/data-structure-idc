/**
 * The Manager class (Generic class) that maintains comparable elements
 * (patients in our case) in two private data structures.
 * Max heap and FIFO queue.
 *
 */
public class Manager <T extends Comparable<T>> {
    private Queue queue;
    private Heap heap;

    /**
     * A standard constructor for the Manager class
     */
    public Manager(){
        this.queue = new Queue();
        this.heap = new Heap();
    }

    /**
     * Add method - add new T to the system, add for both the Heap and the Queue.
     */
    public void add(T t){
        this.queue.add(t);
        this.heap.add(t);
    };

    /**
     * GetByCreationTime method - return the next T (patient) in the queue and remove it from the queue and the heap.
     *
     * @return T - the next T (patient) in the FIFO queue.
     */
    public T getByCreationTime(){
        T currentPatient = (T) this.queue.get();
        this.heap.remove(currentPatient);
        return currentPatient;
    };

    /**
     * GetByPriority method - return the next T (patient) in the heap and remove it from the queue and the heap.
     *
     * @return T - the next T (patient) in the max heap.
     */
    public T getByPriority(){
        T currentPatient = (T) this.heap.get();
        this.queue.remove(currentPatient);
        return currentPatient;
    };

    public Heap getHeap() {
        return heap;
    };

    public Queue getQueue() {
        return queue;
    };
}

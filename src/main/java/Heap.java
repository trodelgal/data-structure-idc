import java.sql.Array;
import java.util.Arrays;

// Heap.java should implement a max heap using an array as learned in class. Priorities should be
// determined according to CompareTo.

/**
 * The Heap class implement a max heap using an array as learned in class.
 *
 * Priorities determined according to Patient CompareTo.
 *
 * This class used for implement priority queue in my system.
 * The manager will use this class to get the next patient by priority.
 */
public class Heap<T extends Comparable<T>> {
    private int size;
    private T[] arr;

    /**
     * A standard constructor for the Heap class
     */
    public Heap(){
        this.arr = (T[]) new Comparable[11];
        this.size = 0;
    }

    /**
     * Add method - add new T to the correct place in the max heap.
     */
    public void add(T t){
        if(t == null)
            return;
        size++;
        if(arr.length == size)
            doubleSize();
        arr[size] = t;
        if (size != 1)
            percolateUp(size);
    };

    /**
     * Get method - return the maximal T in the heap and remove it.
     *
     * @return T - the maximal T in the Heap.
     */
    public T get(){
        if(size == 0)
            return null;
        T first = arr[1];
        if (size == 1) {
            arr[1] = null;
            size --;
        }
        else{
            arr[1] = arr[size];
            arr[size] = null;
            size --;
            percolateDown(1);
        }
        return first;
    };

    /**
     * Remove method - remove the input element from the heap
     *
     * @param t - T object to be remove.
     */
    public void remove(T t){
        if(size == 0)
            return;
        T last = arr[size];
        int i = 1;
        while (i <= size && !arr[i].equals(t)){
            i++;
        };
        if(i>size)
            return;
        arr[i] = last;
        arr[size] = null;
        size --;
        percolateDown(i);
    };

    public int getSize(){
        return size;
    }
    private void doubleSize() {
        arr = Arrays.copyOf(arr, arr.length * 2);
    }

    private void percolateDown(int i){
        T parcolatedPatient = arr[i];
        int leftChild = 2 * i;
        int rightChild = 2 * i + 1;
        while (leftChild <= size){
            int maximalChild = leftChild;
            if (rightChild <= size && arr[leftChild].compareTo(arr[rightChild]) < 0)
                maximalChild = rightChild;
            if(arr[i].compareTo(arr[maximalChild]) > 0)
                return;
            arr[i] = arr[maximalChild];
            arr[maximalChild] = parcolatedPatient;
            i = maximalChild;
            leftChild = maximalChild * 2;
            rightChild = maximalChild * 2 + 1;
        }

    };

    private void percolateUp(int i){
        T parcolatedPatient = arr[i];
        int father = i / 2;
        while (father > 0 && arr[i].compareTo(arr[father]) > 0){
            arr[i] = arr[father];
            arr[father] = parcolatedPatient;
            i = father;
            father = father / 2;
        }
    };
}
import java.util.Arrays;
import java.util.LinkedList;

/**
 * The Sort class provides various sorting algorithms for arrays.
 * It supports sorting arrays of objects that implement the Comparable interface.
 */
public class Sort <T extends Comparable<T>> {
    /**
     * The threshold value for using the simple sort algorithm.
     * If the array length is less than this threshold, simpleSort() will be used instead of other sorting algorithms.
     */
    int naiveSortThreshold = 19;
    /**
     * Constructs a new Sort object.
     */
    public Sort() {}

    /**
     * Sorts the input array using the quick sort algorithm that we learned in class.
     * If the array length is less than the naiveSortThreshold, simpleSort() is used instead.
     *
     * @param array the array to be sorted
     */
    public void quickSortClass(T[] array){
        if (array.length < naiveSortThreshold)
            simpleSort(array, 0, array.length - 1);
        else
            quickSortClass(array, 0, array.length - 1);
    }

    /**
     * Sorts the input array using the quick sort algorithm that we learned in recitation.
     * If the array length is less than the naiveSortThreshold, simpleSort() is used instead.
     *
     * @param array the array to be sorted
     */
    public void quickSortRecitation (T[]array){
        if (array.length < naiveSortThreshold)
            simpleSort(array, 0, array.length - 1);
        else
            quickSortRecitation(array, 0, array.length - 1);
    }

    /**
     * Sorts the input array using the radix sort algorithm with the specified base.
     *
     * @param array the array to be sorted
     * @param base  the base for the radix sort algorithm
     */
    public static void radixSort(int[] array, int base){
        int maxValue = 0;
        for (int i = 0; i < array.length; i++){
            if(array[i] > maxValue)
                maxValue = array[i];
        }
        int j = 1;
        while (j > 0 && j < maxValue){
            countingSort(array,base, j);
            j = j*base;
        }
    }

    /**
     * Sorts the input array using the merge sort algorithm (recursive implementation).
     * If the array length is less than the naiveSortThreshold, simpleSort() is used instead.
     *
     * @param array the array to be sorted
     */
    public void mergeSortRecursive(T[] array){
        if (array.length < naiveSortThreshold)
            simpleSort(array, 0, array.length - 1);
        else
            mergeSortRecursive(array, 0, array.length - 1);

    }

    /**
     * Sorts the input array using the merge sort algorithm (iterative implementation).
     * If the array length is less than the naiveSortThreshold, simpleSort() is used instead.
     *
     * @param array the array to be sorted
     */
    public void mergeSortIterative(T[] array){
        if (array.length < naiveSortThreshold)
            simpleSort(array, 0, array.length - 1);
        else{
            int n = array.length;
            for(int subArr = 1; subArr<n; subArr*=2){
                for(int left = 0; left < n; left = left+2*subArr){
                    int mid = Math.min(left + subArr - 1, n - 1);
                    int right = Math.min(left + 2 * subArr - 1, n - 1);
                    merge(array,left,mid,right);
                }
            }
        }
    }

    /**
     * Sets the threshold value for using the simple sort algorithm.
     *
     * @param threshold the new threshold value
     */
    public void setNaiveSortThreshold(int threshold){
        naiveSortThreshold = threshold;
    }

    /**
     * Sorts the input array using the quick sort algorithm that we learned in class.
     * This is a helper method for the recursive implementation of quickSortClass().
     *
     * @param arr   the array to be sorted
     * @param start the starting index of the subarray to be sorted
     * @param end   the ending index of the subarray to be sorted
     */
    private void quickSortClass(T[]arr, int start, int end){
        if (end > start){
            int q = partitionClass(arr, start, end);
            quickSortClass(arr, start, q-1);
            quickSortClass(arr, q, end);
        }
    }

    /**
     * Sorts the input array using the quick sort algorithm that we learned in recitation.
     * This is a helper method for the recursive implementation of quickSortRecitation().
     *
     * @param arr   the array to be sorted
     * @param start the starting index of the subarray to be sorted
     * @param end   the ending index of the subarray to be sorted
     */
    private void quickSortRecitation(T[]arr, int start, int end){
        if (end > start){
            int q = partitionRecitation(arr, start, end);
            quickSortRecitation(arr, start, q-1);
            quickSortRecitation(arr, q, end);
        }
    }
    /**
     * Sorts the subarray within the given array using the merge sort algorithm (recursive implementation).
     *
     * @param arr the array containing the subarray to be sorted
     * @param start the starting index of the subarray
     * @param end the ending index of the subarray
     */
    private void mergeSortRecursive(T[]arr, int start, int end){
        if(start < end){
            int q = (start+end)/2;
            mergeSortRecursive(arr, start, q);
            mergeSortRecursive(arr, q+1, end);
            merge(arr, start, q, end);
        }
    }

    /**
     * Partitions the subarray for the quick sort algorithm (class implementation).
     *
     * @param arr   the array containing the subarray
     * @param start the starting index of the subarray
     * @param end   the ending index of the subarray
     * @return the partition index
     */
    private int partitionClass(T[]arr, int start, int end){
        T pivot = arr[end];
        int j = end;
        int i = start - 1;
        while (true){
            do{
                j--;
            }
            while (start <= j && pivot.compareTo(arr[j]) < 0 );
            do {
                i++;
            }while (i <= end && pivot.compareTo(arr[i]) >= 0 );
            if(i < j){
                swap(arr, i, j );
            }
            else{
                swap(arr,j+1 , end);
                return j+1;
            }
        }
    }

    /**
     * Partitions the subarray for the quick sort algorithm (recitation implementation).
     *
     * @param arr   the array containing the subarray
     * @param start the starting index of the subarray
     * @param end   the ending index of the subarray
     * @return the partition index
     */
    private int partitionRecitation(T[]arr, int start, int end){
        T pivot = arr[end];
        int i = start - 1;
        for(int j = start; j < end; j++){
            if(pivot.compareTo(arr[j]) >= 0){
                i = i+1;
                swap(arr, i, j);
            }
        }
       swap(arr, i+1, end);
        return i+1;
    }

    /**
     * Merges two subarrays during the merge sort algorithm.
     *
     * @param arr   the array containing the subarrays
     * @param start the starting index of the first subarray
     * @param mid   the ending index of the first subarray and the starting index of the second subarray
     * @param end   the ending index of the second subarray
     */
    private void merge(T[]arr, int start, int mid, int end){
        T[]firstHalf = Arrays.copyOfRange(arr, start, mid + 1);
        T[]secondHalf = Arrays.copyOfRange(arr, mid+1, end + 1);
        int i = 0 ,j = 0;
        for (int k = start; k <= end; k++ ) {
            if(secondHalf.length == j){
                arr[k] = firstHalf[i];
                i++;
            }
            else if(firstHalf.length == i){
                arr[k] = secondHalf[j];
                j++;
            }
            else if( firstHalf[i].compareTo(secondHalf[j]) <= 0) {
                arr[k] = firstHalf[i];
                i++;
            }
            else {
                arr[k] = secondHalf[j];
                j++;
            }
        }
    }

    /**
     * Performs a simple sort on the input array using the bubble sort algorithm.
     *
     * @param arr   the array to be sorted
     * @param start the starting index of the subarray to be sorted
     * @param end   the ending index of the subarray to be sorted
     */
    private void simpleSort(T[] arr, int start, int end){
        for (int i = start; i < end ; i++) {
            for (int j = start; j < start + end-i; j++) {
                if (arr[j].compareTo(arr[j+1]) > 0 ) {
                    swap(arr, j, j+1);
                }
            }
        }
    }

    /**
     * Swaps two elements in the array.
     *
     * @param arr the array containing the elements
     * @param i   the index of the first element
     * @param j   the index of the second element
     */
    private void swap(T[]arr,int i, int j){
        T x = arr[i];
        arr[i] = arr[j];
        arr[j] = x;
    }

    /**
     * Converts the given array of integers to a string representation.
     *
     * @param numbers the array of integers
     */
    private void toString(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
    }

    /**
     * Performs counting sort on the input array with the specified base and divider.
     *
     * @param arr     the array to be sorted
     * @param base    the base for the counting sort algorithm
     * @param divider the divider for determining the current digit to sort
     */
    private static void countingSort(int[]arr,int base, int divider){
        int n = arr.length;
        int[] orderedArr = new int[n];
        int[] counterArr = new int[base];
        for (int i = 0; i < n; i++){
            counterArr[arr[i]/divider % base]++;
        }
        for (int k = 1; k < base; k++){
            counterArr[k] = counterArr[k] + counterArr[k-1];
        }
        for (int j = n-1; j >= 0 ; j--){
            orderedArr[counterArr[arr[j]/divider % base] - 1] = arr[j];
            counterArr[arr[j]/divider % base] --;
        }
        for(int t=0; t<n; t++){
            arr[t] = orderedArr[t];
        }
    }
}

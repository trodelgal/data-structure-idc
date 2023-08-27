import java.util.Arrays;
import java.util.Random;

/**
 * The SortTest class is responsible for performing sorting tests on different sorting algorithms.
 * It measures the duration of each sorting operation and maintains a duration list for analysis.
 */
public class SortTest {

    int numiter = 100;  // Number of iterations for each test
    double[][] durationList = new double[6][numiter];  // Duration list to store the durations of sorting operations
    double[][] durationRadixList = new double[6][numiter];  // Duration list to store the durations of sorting operations
    Integer[] A, B;  // Arrays used for testing
    int[] C;  // Int array for testing radix sort
    String[] sortMethods = {"QC     ","QR     ","MR     ","MI     ","RA     ","JA     "};  // Sort method labels
    long startTime, endTime;  // Variables to measure the duration of sorting operations
    double duration;  // Duration of a sorting operation

    int[] bases = {(int)Math.pow(2,1), (int)Math.pow(2,5), (int)Math.pow(2,10), (int)Math.pow(2,15),
            (int)Math.pow(2,20), (int)Math.pow(2,25)};  // Base values for radix sort tests
    Sort sort = new Sort();  // Instance of the Sort class for accessing sorting algorithms
    Random r = new Random();  // Random number generator for creating random arrays


    /**
     * Performs sorting tests on an array of a given size and type.
     *
     * @param size The size of the array to be sorted
     * @param type The type of array to be generated (RANDOM, INCREASE, DECREASE)
     */
    public void sortTests(int size, TestType type){
        for(int i = 0; i < numiter; i++) {
            A = new Integer[size];
            C = new int[size];
            if(type == TestType.RANDOM)
                randomizeArray(A);
            else if(type == TestType.INCREASE)
                sortArray(A);
            else
                sortOpposite(A);
            for (int t = 0; t < A.length; t++) {
                C[t] = A[t].intValue();
            }
            for (int sortType = 0; sortType <= 5; sortType++){
                try {
                    switch (sortType){
                        case 0:
                            beforeSort();
                            sort.quickSortClass(B);
                            break;
                        case 1:
                            beforeSort();
                            sort.quickSortRecitation(B);
                            break;
                        case 2:
                            beforeSort();
                            sort.mergeSortRecursive(B);
                            break;
                        case 3:
                            beforeSort();
                            sort.mergeSortIterative(B);
                            break;
                        case 4:
                            beforeSort();
                            Arrays.sort(B);
                        case 5:
                            beforeSort();
                            Sort.radixSort(C,(int)Math.pow(2,10));
                            break;
                    }
                    afterSort(i,sortType);
                } catch (StackOverflowError ex){
                    afterSort(i, sortType, true);
                    System.out.println(ex);
                }
            }
        }
        System.out.println("Table of size: " + size + " and " + type + " arrays");
        printDurationList();
    }

    /**
     * Generates random values for an array.
     *
     * @param array The array to be randomized
     */
    private void randomizeArray(Integer[] array) {
        for(int i = 0 ; i < array.length; i++){
            array[i] = Integer.valueOf(r.nextInt(Integer.MAX_VALUE));
        }
    }
    private void randomizeArrayWithRange(int[] array, int range) {
        for(int i = 0 ; i < array.length; i++){
            array[i] = r.nextInt(range);
        }
    }
    private void sortArray(Integer[] a) {
        for(int i = 0 ; i < a.length; i++){
            a[i] = i;
        }
    }

    /**
     * Generates sorted values in decreasing order for an array.
     *
     * @param a The array to be sorted
     */
    private void sortOpposite(Integer[] a) {
        for(int i = 0 ; i < a.length; i++){
            a[i] = a.length - i;
        }
    }

    /**
     * Performs operations before starting a sorting operation, such as assigning the array to be sorted and recording
     * the start time.
     */
    private void beforeSort(){
        B = A;
        startTime = System.currentTimeMillis();
    }
    /**
     * Performs operations after completing a sorting operation, such as recording the end time and calculating
     * the duration.
     *
     * @param i The index of the current iteration
     * @param j The index of the current sorting algorithm
     */
    private void afterSort(int i, int sortType){
        endTime = System.currentTimeMillis();
        duration = ((double) (endTime - startTime));
        durationList[sortType][i] = duration;
    }
    /**
     * Performs operations after completing a sorting operation that resulted in a stack overflow error.
     * It sets the duration to 0 for error cases.
     *
     * @param i     The index of the current iteration
     * @param sortType     The index of the current sorting algorithm
     * @param error Indicates if a stack overflow error occurred
     */
    private void afterSort(int i, int sortType, boolean error){
        if (error)
            durationList[sortType][i] = 0.0;
        else {
            endTime = System.currentTimeMillis();
            duration = ((double) (endTime - startTime));
            durationList[sortType][i] = duration;
        }
    }

    /**
     * Prints the duration list, which contains the average durations of each sorting algorithm.
     */
    private void printDurationList() {
        double sum;
        double deviation;
        double avg;
        for (int row = 0; row < durationList.length; row++) {
            sum = 0;
            deviation = 0;
            avg = 0;
            for (int col = 0; col < durationList[0].length; col++) {
                sum += durationList[row][col];
            }
            System.out.println();
            avg = sum/numiter;
            System.out.println("avg = " + avg);
            for (int col = 0; col < durationList[0].length; col++) {
                deviation += Math.pow(avg - durationList[row][col],2);
            }
            deviation = Math.sqrt(deviation/numiter);
            System.out.print("dev: " + deviation + "  ");
        System.out.println();
        }
    }

    /**
     * Performs radix sort tests on arrays of different sizes and types.
     *
     */
    public void radixSortTests(int range){
        double sum;
        double deviation;
        double avg;
        for(int i = 0; i < numiter; i++) {
            C = new int[500000];
            randomizeArrayWithRange(C,range);
            for(int j = 0; j < bases.length; j++){
                startTime = System.currentTimeMillis();
                Sort.radixSort(C,bases[j]);
                endTime = System.currentTimeMillis();
                duration = ((double) (endTime - startTime));
                durationRadixList[j][i] = duration;
            }
        }
        for (int row = 0; row < durationRadixList.length; row++) {
            sum = 0;
            deviation = 0;
            avg = 0;
            for (int col = 0; col < durationRadixList[0].length; col++) {
                sum += durationRadixList[row][col];
            }
            avg = sum/numiter;
            System.out.println();
            System.out.println("avg = " + avg);
            for (int col = 0; col < durationRadixList.length; col++) {
                deviation += Math.pow(avg - durationRadixList[row][col],2);
            }
            deviation = Math.sqrt(deviation/numiter);
            System.out.print("dev: " + deviation + "  ");
            System.out.println();
        }
    }
}


enum TestType {
    RANDOM,
    INCREASE,
    DECREASE
}

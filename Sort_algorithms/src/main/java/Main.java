/**

 The main class for executing sorting tests.
 */
public class Main {
    // An array of input sizes for the sorting tests
    static int[] inputSizes = {10000, 50000, 100000, 500000,1000000};
//    static int[] inputSizes = {500000,1000000};
    static int[] ranges = {(int)Math.pow(2,10), (int)Math.pow(2,20), (int)Math.pow(2,30)};  // Array sizes for radix sort tests
//    static int[] inputSizes = {10000};
    // An instance of the SortTest class for performing sorting tests
    static SortTest test = new SortTest();

    /**

     The main method that starts the execution of sorting tests.

     @param args the command-line arguments
     */
    public static void main(String[] args){
        for (int i = 0; i < inputSizes.length; i++) {
            System.out.println("Starting...");
            test.sortTests(inputSizes[i], TestType.RANDOM);
            test.sortTests(inputSizes[i], TestType.INCREASE);
            test.sortTests(inputSizes[i], TestType.DECREASE);
            test.radixSortTests(ranges[0]);
            test.radixSortTests(ranges[1]);
            test.radixSortTests(ranges[2]);
        }
    }

}


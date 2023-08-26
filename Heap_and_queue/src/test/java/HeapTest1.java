import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class HeapIntegerTest {
    static final int NUM_OF_INTEGERS = 10000;
    static final int NUM_OF_INTEGERS_FOR_REMOVE = 500;
    static final int LARGE_NUM_OF_INTEGERS = 10000;
    static final int NUM_OF_INTEGERS_FOR_STRESS = 100;
    static final int NUM_OF_REPETITION = 10;
    static final int NUM_OF_STRESS_TESTS = 50;
    static final Random random = new Random();

    java.util.Queue<Integer> builtinPriorityQueue;
    Heap<Integer> heap;
    ArrayList<Integer> randomIntegers;


    @BeforeEach
    void setUp() {
        builtinPriorityQueue = new java.util.PriorityQueue<>(Collections.reverseOrder());
        heap = new Heap<>();
        randomIntegers = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        builtinPriorityQueue = null;
        heap = null;
        randomIntegers = null;
    }

    void populateQueues(int[] array) {
        for (int num : array) {
            heap.add(num);
            builtinPriorityQueue.add(num);
        }
    }

    void populateQueues() {
        populateQueues(NUM_OF_INTEGERS);
    }
    void populateQueues(int size) {
        for (int num = 0; num < size; num++) {
            heap.add(num);
            builtinPriorityQueue.add(num);
        }
    }

    void populateQueuesForRemove() {
        populateQueues(NUM_OF_INTEGERS_FOR_REMOVE);
    }

    void populateQueuesRandomly() {
        populateQueuesRandomly(NUM_OF_INTEGERS);
    }

    void populateQueuesRandomlyForRemove() {
        populateQueuesRandomly(NUM_OF_INTEGERS_FOR_REMOVE, NUM_OF_INTEGERS_FOR_REMOVE);
    }

    void populateQueuesRandomlyWithRepetitionForRemove() {
        populateQueuesRandomly(NUM_OF_INTEGERS_FOR_REMOVE, NUM_OF_REPETITION);
    }

    void populateQueuesRandomlyLarge() {
        populateQueuesRandomly(LARGE_NUM_OF_INTEGERS, LARGE_NUM_OF_INTEGERS);
    }

    void populateQueuesRandomlyWithRepetition() {
        populateQueuesRandomly(NUM_OF_REPETITION);
    }

    void populateQueuesRandomlyForStress() {
        populateQueuesRandomly(NUM_OF_INTEGERS_FOR_STRESS,
                NUM_OF_INTEGERS_FOR_STRESS);
    }

    void populateQueuesRandomly(int upperBound) {
        populateQueuesRandomly(NUM_OF_INTEGERS, upperBound);
    }

    void populateQueuesRandomly(int size, int upperBound) {
        for (int i = 0; i < size; i++) {
            int num = random.nextInt(upperBound);
            heap.add(num);
            builtinPriorityQueue.add(num);
            randomIntegers.add(num);
        }
    }

    void assertGetResults() {
        assertGetResults(heap, builtinPriorityQueue);
    }

    void assertGetResults(Heap<Integer> heap2, java.util.Queue<Integer> builtinPriorityQueue2) {
        while (!builtinPriorityQueue2.isEmpty()) {
            int expectedInteger = builtinPriorityQueue2.poll();
            int actualInteger = heap2.get();
            assertEquals(expectedInteger, actualInteger);
        }
        assertNull(heap2.get());
    }

    private Heap<Integer> copyHeap(Heap<Integer> heap) {
        Heap<Integer> tempHeap = new Heap<>();
        Heap<Integer> copiedHeap = new Heap<>();
        Integer num = heap.get();
        while (num != null) {
            tempHeap.add(num);
            copiedHeap.add(num);
            num = heap.get();
        }
        num = tempHeap.get();
        while (num != null) {
            heap.add(num);
            num = tempHeap.get();
        }
        return copiedHeap;
    }

    private void assertGetResultsPure() {
        Heap<Integer> copiedHeap = copyHeap(heap);
        java.util.Queue<Integer> copiedBuiltinPriorityQueue =
                new java.util.PriorityQueue<>(builtinPriorityQueue);
        assertGetResults(copiedHeap, copiedBuiltinPriorityQueue);
    }

    void assertRemoveResults() {
        for (int num = 0; num < NUM_OF_INTEGERS; num++) {
            builtinPriorityQueue.remove(num);
            heap.remove(num);
            assertGetResultsPure();
        }
        assertNull(heap.get());
    }

    void assertRemoveResultsRandomly() {
        for (int num : randomIntegers) {
            builtinPriorityQueue.remove(num);
            heap.remove(num);
            assertGetResultsPure();
        }
        assertNull(heap.get());
    }

    void assertRandomRemoveResultsRandomly() {
        UniqueRandom uniqueRandom = new UniqueRandom(randomIntegers.size());
        for (int num : randomIntegers) {
            int numToRemove = randomIntegers.get(uniqueRandom.nextInt());
            builtinPriorityQueue.remove(num);
            heap.remove(num);
            assertGetResultsPure();
        }
        assertNull(heap.get());
    }

    private void removeFromHeap(int[] array) {
        for (int numToRemove : array) {
            heap.remove(numToRemove);
            builtinPriorityQueue.remove(numToRemove);
        }
    }

    private void removeFromHeapRandomly(int[] array) {
        UniqueRandom uniqueRandom = new UniqueRandom(array.length);
        for (int num : array) {
            int numToRemove = array[uniqueRandom.nextInt()];
            heap.remove(numToRemove);
            builtinPriorityQueue.remove(numToRemove);
        }
    }

    @Test
    void testAddAndGet() {
        populateQueues();
        assertGetResults();
    }

    @Test
    void testAddAndGetFullRandom() {
        populateQueuesRandomly();
        assertGetResults();
    }

    @Test
    void testAddAndGetFullRandomLarge() {
        populateQueuesRandomlyLarge();
        assertGetResults();
    }


    @Test
    void testAddAndGetRepetitionRandom() {
        populateQueuesRandomlyWithRepetition();
        assertGetResults();
    }

    @Test
    void testStressAddAndGetRandom() {
        for (int i = 0; i < NUM_OF_STRESS_TESTS; i++) {
            populateQueuesRandomlyForStress();
            assertGetResults();
        }
    }

    @Test
    void testAddAndRemove() {
        populateQueuesForRemove();
        assertRemoveResults();
    }

    @Test
    void testAddAndRemoveFullRandom() {
        populateQueuesRandomlyForRemove();
        assertRemoveResultsRandomly();
    }

    @Test
    void testAddAndRemoveRepetitionRandom() {
        populateQueuesRandomlyWithRepetitionForRemove();
        assertRemoveResultsRandomly();
    }

    @Test
    void testStressAddAndRemoveRandom() {
        for (int i = 0; i < NUM_OF_STRESS_TESTS; i++) {
            populateQueuesRandomlyForStress();
            assertRemoveResultsRandomly();
        }
    }

    @Test
    void testAddAndRandomRemoveFullRandom() {
        populateQueuesRandomlyForRemove();
        assertRandomRemoveResultsRandomly();
    }

    @Test
    void testAddAndRandomRemoveRepetitionRandom() {
        populateQueuesRandomlyWithRepetitionForRemove();
        assertRandomRemoveResultsRandomly();
    }

    @Test
    void testStressAddAndRandomRemoveRandom() {
        for (int i = 0; i < NUM_OF_STRESS_TESTS; i++) {
            populateQueuesRandomlyForStress();
            assertRandomRemoveResultsRandomly();
        }
    }

    @Test
    void testEmptyHeap() {
        assertNull(heap.get());
    }

    @Test
    void testHeapRemove() {
        int[] array = {8, 7, 5, 3, 5, 1, 4, 2};
        populateQueues(array);
        int numToRemove = 5;
        heap.remove(numToRemove);
        builtinPriorityQueue.remove(numToRemove);
        assertGetResults();
    }

    @Test
    void testGetEdgeCaseSamePriorityChildren() {
        int[] array = {4, 10, 10, 15, 16, 20, 21};
        populateQueues(array);
        assertGetResults();
    }

    @Test
    void testAddAndGet2() {
        int[] array = {4, 10, 10, 15, 16, 20, 21};
        populateQueues(array);
        assertGetResults();
    }

    @Test
    void testAddAndGet3() {
        int[] array = {8, 7, 6, 3, 5, 5, 4, 2, 1};
        populateQueues(array);
        assertGetResults();
    }

    @Test
    void testAddAndGet4() {
        int[] array = {9, 8, 5, 6, 7, 2, 5, 1, 4, 3};
        populateQueues(array);
        assertGetResults();
    }

    @Test
    void testAddAndGetSameValue() {
        int[] array = {5, 5, 5, 5, 5, 5, 5};
        populateQueues(array);
        assertGetResults();
    }

    @Test
    void testRemoveWithPercolateUp() {
        int[] array = {1, 3, 5, 7, 9};
        populateQueues(array);
        int numToRemove = 3;
        heap.remove(numToRemove);
        builtinPriorityQueue.remove(numToRemove);
        assertGetResults();
    }

    @Test
    void testRemoveWithPercolateUp2() {
        int[] array = {1, 3, 5, 7, 9};
        populateQueues(array);
        int numToRemove = 7;
        heap.remove(numToRemove);
        builtinPriorityQueue.remove(numToRemove);
        assertGetResults();
    }

    @Test
    void testRemoveWithPercolateUp3() {
        int[] array = {8, 4, 7, 4, 3, 5, 6, 3, 2, 2, 1, 3, 2, 4, 5};
        populateQueues(array);
        int numToRemove = 1;
        heap.remove(numToRemove);
        builtinPriorityQueue.remove(numToRemove);
        assertGetResults();
    }

    @Test
    void testRemoveWithPercolateDown() {
        int[] array = {9, 7, 5, 3, 1};
        populateQueues(array);
        int numToRemove = 7;
        heap.remove(numToRemove);
        builtinPriorityQueue.remove(numToRemove);
        assertGetResults();
    }

    @Test
    void testRemoveWithPercolateDown2() {
        int[] array = {20, 15, 10, 12, 8, 7, 6};
        populateQueues(array);
        int numToRemove = 15;
        heap.remove(numToRemove);
        builtinPriorityQueue.remove(numToRemove);
        assertGetResults();
    }

    @Test
    void testRemoveWithOneElementHeap() {
        int numToAdd = 4;
        heap.add(numToAdd);
        builtinPriorityQueue.add(numToAdd);
        heap.remove(numToAdd);
        builtinPriorityQueue.remove(numToAdd);
        assertGetResults();
    }

    @Test
    void testRemoveNonexistentElement() {
        int[] array = {4, 6, 2};
        populateQueues(array);
        int numToRemove = 99;
        heap.remove(numToRemove);
        builtinPriorityQueue.remove(numToRemove);
        assertGetResults();
    }

    @Test
    void testRemoveAllElements() {
        int[] array = {4, 6, 2};
        populateQueues(array);
        removeFromHeap(array);
        assertGetResults();
    }

    @Test
    void testRemoveDuplicates() {
        int[] array = {4, 6, 2, 6, 4};
        populateQueues(array);
        removeFromHeapRandomly(array);
        assertGetResults();
    }

    @Test
    void testRemoveRandom() {
        int[] array = {8, 7, 6, 3, 5, 5, 4, 2, 1};
        populateQueues(array);
        removeFromHeapRandomly(array);
        assertGetResults();
    }
}


class HeapPatientTest {
    static final int NUM_OF_PATIENTS = 2000;
    static final int NUM_OF_PATIENTS_FOR_REMOVE = 500;
    static final int LARGE_NUM_OF_PATIENTS = 50000;
    static final int NUM_OF_PATIENTS_FOR_STRESS = 50;
    static final int NUM_OF_REPETITION = 10;
    static final int NUM_OF_STRESS_TESTS = 50;
    static final Random random = new Random();

    java.util.Queue<Patient> builtinPriorityQueue;
    Heap<Patient> heap;
    ArrayList<Patient> patientList;
    Heap<Patient> copiedHeap;
    java.util.Queue<Patient> copiedBuiltinPriorityQueue;


    @BeforeEach
    void setUp() {
        builtinPriorityQueue = new java.util.PriorityQueue<>(Collections.reverseOrder());
        heap = new Heap<>();
        patientList = new ArrayList<>();
        copiedHeap = new Heap<>();
        copiedBuiltinPriorityQueue = new java.util.PriorityQueue<>(Collections.reverseOrder());
    }

    @AfterEach
    void tearDown() {
        builtinPriorityQueue = null;
        heap = null;
        patientList = null;
        copiedHeap = null;
        copiedBuiltinPriorityQueue = null;
    }

    void populateQueues() {
        populateQueues(NUM_OF_PATIENTS);
    }
    void populateQueues(int size) {
        for (int priority = 0; priority < size; priority++) {
            Patient patient = new Patient(priority, false);
            heap.add(patient);
            builtinPriorityQueue.add(patient);
            patientList.add(patient);
        }
    }

    void populateQueuesForRemove() {
        populateQueues(NUM_OF_PATIENTS_FOR_REMOVE);
    }

    void populateQueuesRandomly() {
        populateQueuesRandomly(NUM_OF_PATIENTS);
    }

    void populateQueuesRandomlyForRemove() {
        populateQueuesRandomly(NUM_OF_PATIENTS_FOR_REMOVE, NUM_OF_PATIENTS_FOR_REMOVE);
    }

    void populateQueuesRandomlyWithRepetitionForRemove() {
        populateQueuesRandomly(NUM_OF_PATIENTS_FOR_REMOVE, NUM_OF_REPETITION);
    }

    void populateQueuesRandomlyLarge() {
        populateQueuesRandomly(LARGE_NUM_OF_PATIENTS, LARGE_NUM_OF_PATIENTS);
    }

    void populateQueuesRandomlyWithRepetition() {
        populateQueuesRandomly(NUM_OF_REPETITION);
    }

    void populateQueuesRandomlyForStress() {
        populateQueuesRandomly(NUM_OF_PATIENTS_FOR_STRESS,
                NUM_OF_PATIENTS_FOR_STRESS);
    }

    void populateQueuesRandomly(int upperBound) {
        populateQueuesRandomly(NUM_OF_PATIENTS, upperBound);
    }

    void populateQueuesRandomly(int size, int upperBound) {
        for (int i = 0; i < size; i++) {
            Patient patient = new Patient(
                    random.nextInt(upperBound),
                    random.nextBoolean());
            heap.add(patient);
            builtinPriorityQueue.add(patient);
            patientList.add(patient);
        }
    }

    void assertGetResults() {
        assertGetResults(heap, builtinPriorityQueue);
    }

    void assertGetResults(Heap<Patient> heap2, java.util.Queue<Patient> builtinPriorityQueue2) {
        while (!builtinPriorityQueue2.isEmpty()) {
            Patient expectedPatient = builtinPriorityQueue2.poll();
            Patient actualPatient = heap2.get();

            assertEquals(expectedPatient.getPriority(), actualPatient.getPriority());
        }
        Patient res2 = heap2.get();
        System.out.println("res2: " + res2);
        assertNull(res2);
    }

    private void copyHeap() {
        Heap<Patient> tempHeap = new Heap<>();
        Patient patient = heap.get();
        while (patient != null) {
            tempHeap.add(patient);
            copiedHeap.add(patient);
            copiedBuiltinPriorityQueue.add(patient);
            patient = heap.get();
        }
        patient = tempHeap.get();
        while (patient != null) {
            heap.add(patient);
            patient = tempHeap.get();
        }
    }

    private void assertGetResultsPure() {
        copyHeap();
        assertGetResults(copiedHeap, copiedBuiltinPriorityQueue);
    }

    void assertRemoveResults() {
        for (Patient patient : patientList) {
            builtinPriorityQueue.remove(patient);
            heap.remove(patient);
            assertGetResultsPure();
        }
        Patient res = heap.get();
        System.out.println("res: " + res);
        assertNull(res);
    }

    void assertRandomRemoveResultsRandomly() {
        UniqueRandom uniqueRandom = new UniqueRandom(patientList.size());
        for (Patient patient : patientList) {
            Patient patientToRemove = patientList.get(uniqueRandom.nextInt());
            builtinPriorityQueue.remove(patientToRemove);
            heap.remove(patientToRemove);
            assertGetResultsPure();
        }
        assertNull(heap.get());
    }

    @Test
    void testAddAndGet() {
        populateQueues();
        assertGetResults();
    }

    @Test
    void testAddAndGetFullRandom() {
        populateQueuesRandomly();
        assertGetResults();
    }

    @Test
    void testAddAndGetFullRandomLarge() {
        populateQueuesRandomlyLarge();
        assertGetResults();
    }


    @Test
    void testAddAndGetRepetitionRandom() {
        populateQueuesRandomlyWithRepetition();
        assertGetResults();
    }

    @Test
    void testStressAddAndGetRandom() {
        for (int i = 0; i < NUM_OF_STRESS_TESTS; i++) {
            populateQueuesRandomlyForStress();
            assertGetResults();
        }
    }

    @Test
    void testAddAndRemove() {
        populateQueuesForRemove();
        assertRemoveResults();
    }

    @Test
    void testAddAndRemoveFullRandom() {
        populateQueuesRandomlyForRemove();
        assertRemoveResults();
    }

    @Test
    void testAddAndRemoveRepetitionRandom() {
        populateQueuesRandomlyWithRepetitionForRemove();
        assertRemoveResults();
    }

    @Test
    void testStressAddAndRemoveRandom() {
        for (int i = 0; i < NUM_OF_STRESS_TESTS; i++) {
            populateQueuesRandomlyForStress();
            assertRemoveResults();
        }
    }

    @Test
    void testAddAndRandomRemoveFullRandom() {
        populateQueuesRandomlyForRemove();
        assertRandomRemoveResultsRandomly();
    }

    @Test
    void testAddAndRandomRemoveRepetitionRandom() {
        populateQueuesRandomlyWithRepetitionForRemove();
        assertRandomRemoveResultsRandomly();
    }

    @Test
    void testStressAddAndRandomRemoveRandom() {
        for (int i = 0; i < NUM_OF_STRESS_TESTS; i++) {
            populateQueuesRandomlyForStress();
            assertRandomRemoveResultsRandomly();
        }
    }

    @Test
    void testEmptyHeap() {
        assertNull(heap.get());
    }
}

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class QueueIntegerTest {
    static final int NUM_OF_INTEGERS = 2500;
    static final int LARGE_NUM_OF_INTEGERS = 250000;
    static final int NUM_OF_INTEGERS_FOR_STRESS = 100;
    static final int NUM_OF_REPETITION = 10;
    static final int NUM_OF_STRESS_TESTS = 50;
    static final Random random = new Random();

    java.util.Queue<Integer> builtinOrderedQueue;
    Queue<Integer> queue;
    ArrayList<Integer> randomIntegers;


    @BeforeEach
    void setUp() {
        builtinOrderedQueue = new java.util.LinkedList<>();
        queue = new Queue<>();
        randomIntegers = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        builtinOrderedQueue = null;
        queue = null;
        randomIntegers = null;
    }

    void populateQueues(int[] array) {
        for (int num : array) {
            queue.add(num);
            builtinOrderedQueue.add(num);
        }
    }

    void populateQueues() {
        for (int num = 0; num < NUM_OF_INTEGERS; num++) {
            queue.add(num);
            builtinOrderedQueue.add(num);
        }
    }

    void populateQueuesRandomly() {
        populateQueuesRandomly(NUM_OF_INTEGERS);
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
            queue.add(num);
            builtinOrderedQueue.add(num);
            randomIntegers.add(num);
        }
    }

    void assertGetResults() {
        assertGetResults(queue, builtinOrderedQueue);
    }

    void assertGetResults(Queue<Integer> queue2, java.util.Queue<Integer> builtinPriorityQueue2) {
        while (!builtinPriorityQueue2.isEmpty()) {
            int expectedInteger = builtinPriorityQueue2.poll();
            int actualInteger = queue2.get();
            assertEquals(expectedInteger, actualInteger);
        }
        assertNull(queue2.get());
    }

    private Queue<Integer> copyQueue(Queue<Integer> heap) {
        Queue<Integer> tempHeap = new Queue<>();
        Queue<Integer> copiedHeap = new Queue<>();
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
        Queue<Integer> copiedQueue = copyQueue(queue);
        java.util.Queue<Integer> copiedBuiltinPriorityQueue =
                new java.util.LinkedList<>(builtinOrderedQueue);
        assertGetResults(copiedQueue, copiedBuiltinPriorityQueue);
    }

    void assertRemoveResults() {
        for (int num = 0; num < NUM_OF_INTEGERS; num++) {
            builtinOrderedQueue.remove(num);
            queue.remove(num);
            assertGetResultsPure();
        }
        assertNull(queue.get());
    }

    void assertRemoveResultsRandomly() {
        for (int num : randomIntegers) {
            builtinOrderedQueue.remove(num);
            queue.remove(num);
            assertGetResultsPure();
        }
        assertNull(queue.get());
    }

    void assertRandomRemoveResultsRandomly() {
        UniqueRandom uniqueRandom = new UniqueRandom(randomIntegers.size());
        for (int num : randomIntegers) {
            int numToRemove = randomIntegers.get(uniqueRandom.nextInt());
            builtinOrderedQueue.remove(num);
            queue.remove(num);
            assertGetResultsPure();
        }
        assertNull(queue.get());
    }

    private void removeFromHeap(int[] array) {
        for (int numToRemove : array) {
            queue.remove(numToRemove);
            builtinOrderedQueue.remove(numToRemove);
        }
    }

    private void removeFromHeapRandomly(int[] array) {
        UniqueRandom uniqueRandom = new UniqueRandom(array.length);
        for (int num : array) {
            int numToRemove = array[uniqueRandom.nextInt()];
            queue.remove(numToRemove);
            builtinOrderedQueue.remove(numToRemove);
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
        populateQueues();
        assertRemoveResults();
    }

    @Test
    void testAddAndRemoveFullRandom() {
        populateQueuesRandomly();
        assertRemoveResultsRandomly();
    }

    @Test
    void testAddAndRemoveRepetitionRandom() {
        populateQueuesRandomlyWithRepetition();
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
        populateQueuesRandomly();
        assertRandomRemoveResultsRandomly();
    }

    @Test
    void testAddAndRandomRemoveRepetitionRandom() {
        populateQueuesRandomlyWithRepetition();
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
        assertNull(queue.get());
    }

    @Test
    void testHeapRemove() {
        int[] array = {8, 7, 5, 3, 5, 1, 4, 2};
        populateQueues(array);
        int numToRemove = 5;
        queue.remove(numToRemove);
        builtinOrderedQueue.remove(numToRemove);
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
        queue.remove(numToRemove);
        builtinOrderedQueue.remove(numToRemove);
        assertGetResults();
    }

    @Test
    void testRemoveWithPercolateUp2() {
        int[] array = {1, 3, 5, 7, 9};
        populateQueues(array);
        int numToRemove = 7;
        queue.remove(numToRemove);
        builtinOrderedQueue.remove(numToRemove);
        assertGetResults();
    }

    @Test
    void testRemoveWithPercolateUp3() {
        int[] array = {8, 4, 7, 4, 3, 5, 6, 3, 2, 2, 1, 3, 2, 4, 5};
        populateQueues(array);
        int numToRemove = 1;
        queue.remove(numToRemove);
        builtinOrderedQueue.remove(numToRemove);
        assertGetResults();
    }

    @Test
    void testRemoveWithPercolateDown() {
        int[] array = {9, 7, 5, 3, 1};
        populateQueues(array);
        int numToRemove = 7;
        queue.remove(numToRemove);
        builtinOrderedQueue.remove(numToRemove);
        assertGetResults();
    }

    @Test
    void testRemoveWithPercolateDown2() {
        int[] array = {20, 15, 10, 12, 8, 7, 6};
        populateQueues(array);
        int numToRemove = 15;
        queue.remove(numToRemove);
        builtinOrderedQueue.remove(numToRemove);
        assertGetResults();
    }

    @Test
    void testRemoveWithOneElementHeap() {
        int numToAdd = 4;
        queue.add(numToAdd);
        builtinOrderedQueue.add(numToAdd);
        queue.remove(numToAdd);
        builtinOrderedQueue.remove(numToAdd);
        assertGetResults();
    }

    @Test
    void testRemoveNonexistentElement() {
        int[] array = {4, 6, 2};
        populateQueues(array);
        int numToRemove = 99;
        queue.remove(numToRemove);
        builtinOrderedQueue.remove(numToRemove);
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


class QueuePatientTest {
    static final int NUM_OF_PATIENTS = 2500;
    static final int LARGE_NUM_OF_PATIENTS = 100000;
    static final int NUM_OF_PATIENTS_FOR_STRESS = 100;
    static final int NUM_OF_REPETITION = 10;
    static final int NUM_OF_STRESS_TESTS = 50;
    static final Random random = new Random();

    java.util.Queue<Patient> builtinOrderedQueue;
    Queue<Patient> queue;
    ArrayList<Patient> patientList;
    Queue<Patient> copiedQueue;
    java.util.Queue<Patient> copiedbuiltinOrderedQueue;

    @BeforeEach
    void setUp() {
        builtinOrderedQueue = new java.util.LinkedList<>();
        queue = new Queue<>();
        patientList = new ArrayList<>();
        copiedQueue = new Queue<>();
        copiedbuiltinOrderedQueue = new java.util.LinkedList<>();
    }

    @AfterEach
    void tearDown() {
        builtinOrderedQueue = null;
        queue = null;
        patientList = null;
        copiedQueue = null;
        copiedbuiltinOrderedQueue = null;
    }

    void populateQueues() {
        for (int priority = 0; priority < NUM_OF_PATIENTS; priority++) {
            Patient patient = new Patient(priority, false);
            queue.add(patient);
            builtinOrderedQueue.add(patient);
            patientList.add(patient);
        }
    }

    void populateQueuesRandomly() {
        populateQueuesRandomly(NUM_OF_PATIENTS);
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
            queue.add(patient);
            builtinOrderedQueue.add(patient);
            patientList.add(patient);
        }
    }

    void assertGetResults() {
        assertGetResults(queue, builtinOrderedQueue);
    }

    void assertGetResults(Queue<Patient> queue2, java.util.Queue<Patient> builtinPriorityQueue2) {
        while (!builtinPriorityQueue2.isEmpty()) {
            Patient expectedPatient = builtinPriorityQueue2.poll();
            Patient actualPatient = queue2.get();
            assertEquals(expectedPatient, actualPatient);
        }
        assertNull(queue2.get());
    }

    private void copyQueue() {
        Queue<Patient> tempHeap = new Queue<>();
        Patient patient = queue.get();
        while (patient != null) {
            tempHeap.add(patient);
            copiedQueue.add(patient);
            copiedbuiltinOrderedQueue.add(patient);
            patient = queue.get();
        }
        patient = tempHeap.get();
        while (patient != null) {
            queue.add(patient);
            patient = tempHeap.get();
        }
    }

    private void assertGetResultsPure() {
        copyQueue();
        assertGetResults(copiedQueue, copiedbuiltinOrderedQueue);
    }

    void assertRemoveResults() {
        for (Patient patient : patientList) {
            builtinOrderedQueue.remove(patient);
            queue.remove(patient);
            assertGetResultsPure();
        }
        assertNull(queue.get());
    }

    void assertRandomRemoveResultsRandomly() {
        UniqueRandom uniqueRandom = new UniqueRandom(patientList.size());
        for (Patient patient : patientList) {
            Patient patientToRemove = patientList.get(uniqueRandom.nextInt());
            builtinOrderedQueue.remove(patientToRemove);
            queue.remove(patientToRemove);
            assertGetResultsPure();
        }
        assertNull(queue.get());
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
        populateQueues();
        assertRemoveResults();
    }

    @Test
    void testAddAndRemoveFullRandom() {
        populateQueuesRandomly();
        assertRemoveResults();
    }

    @Test
    void testAddAndRemoveRepetitionRandom() {
        populateQueuesRandomlyWithRepetition();
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
        populateQueuesRandomly();
        assertRandomRemoveResultsRandomly();
    }

    @Test
    void testAddAndRandomRemoveRepetitionRandom() {
        populateQueuesRandomlyWithRepetition();
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
        assertNull(queue.get());
    }
}

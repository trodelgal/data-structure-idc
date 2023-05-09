import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IntegerWrapper implements Comparable<IntegerWrapper> {
    private final Integer integer;

    public IntegerWrapper(Integer integer) {
        this.integer = integer;
    }

    public Integer getInteger() {
        return integer;
    }

    @Override
    public int compareTo(IntegerWrapper o) {
        return integer.compareTo(o.integer);
    }

    @Override
    public String toString() {
        return integer.toString();
    }
}

class ManagerIntegerTest {
    static final int NUM_OF_INTEGERS = 20000;
    static final int NUM_OF_INTEGERS_FOR_STRESS = 1000;
    static final int NUM_OF_REPETITION = 10;
    static final int NUM_OF_STRESS_TESTS = 500;
    static final Random random = new Random();

    java.util.Queue<IntegerWrapper> builtinOrderedQueue;
    java.util.Queue<IntegerWrapper> builtinPriorityQueue;
    Manager<IntegerWrapper> manager;


    @BeforeEach
    void setUp() {
        builtinOrderedQueue = new java.util.LinkedList<>();
        builtinPriorityQueue = new java.util.PriorityQueue<>(Collections.reverseOrder());
        manager = new Manager<>();
    }

    @AfterEach
    void tearDown() {
        builtinOrderedQueue = null;
        builtinPriorityQueue = null;
        manager = null;
    }

    void populateQueues() {
        for (int i = 0; i < NUM_OF_INTEGERS; i++) {
            IntegerWrapper integerWrapper = new IntegerWrapper(i);
            manager.add(integerWrapper);
            builtinPriorityQueue.add(integerWrapper);
            builtinOrderedQueue.add(integerWrapper);
        }
    }

    void populateQueuesRandomly() {
        populateQueuesRandomly(NUM_OF_INTEGERS);
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
            Integer num = random.nextInt(upperBound);
            IntegerWrapper integerWrapper = new IntegerWrapper(num);
            manager.add(integerWrapper);
            builtinPriorityQueue.add(integerWrapper);
            builtinOrderedQueue.add(integerWrapper);
        }
    }

    void assertPriorityPollResults() {
        while (!builtinPriorityQueue.isEmpty()) {
            IntegerWrapper expectedInteger = builtinPriorityQueue.poll();
            IntegerWrapper actualInteger = manager.getByPriority();
            assertEquals(expectedInteger.getInteger(), actualInteger.getInteger());
        }
        assertNull(manager.getByPriority());
    }

    void assertOrderedPollResults() {
        while (!builtinOrderedQueue.isEmpty()) {
            IntegerWrapper expectedInteger = builtinOrderedQueue.poll();
            IntegerWrapper actualInteger = manager.getByCreationTime();
            assertEquals(expectedInteger, actualInteger);
        }
        assertNull(manager.getByCreationTime());
    }

    void assertPriorityAndOrderedPollResults() {
        while (!builtinPriorityQueue.isEmpty() && !builtinOrderedQueue.isEmpty()) {
            IntegerWrapper expectedInteger = builtinPriorityQueue.poll();
            builtinOrderedQueue.remove(expectedInteger);
            IntegerWrapper actualInteger = manager.getByPriority();
            assertEquals(expectedInteger, actualInteger);
            expectedInteger = builtinOrderedQueue.poll();
            builtinPriorityQueue.remove(expectedInteger);
            actualInteger = manager.getByCreationTime();
            assertEquals(expectedInteger, actualInteger);

        }
        assertNull(manager.getByPriority());
        assertNull(manager.getByCreationTime());
    }

    void assertPriorityAndOrderedPollResultsRandomly() {
        while (!builtinPriorityQueue.isEmpty() && !builtinOrderedQueue.isEmpty()) {
            IntegerWrapper expectedInteger;
            IntegerWrapper actualInteger;
            if (random.nextBoolean()) {
                expectedInteger = builtinPriorityQueue.poll();
                builtinOrderedQueue.remove(expectedInteger);
                actualInteger = manager.getByPriority();
            } else {
                expectedInteger = builtinOrderedQueue.poll();
                builtinPriorityQueue.remove(expectedInteger);
                actualInteger = manager.getByCreationTime();
            }
            assertEquals(expectedInteger.getInteger(), actualInteger.getInteger());
        }
        assertNull(manager.getByPriority());
        assertNull(manager.getByCreationTime());
    }

    @Test
    void testGetByPriority() {
        populateQueues();
        assertPriorityPollResults();
    }

    @Test
    void testGetByCreationTime() {
        populateQueues();
        assertOrderedPollResults();
    }

    @Test
    void testGetByPriorityAndByCreationTime() {
        populateQueues();
        assertPriorityAndOrderedPollResults();
    }

    @Test
    void testGetByPriorityFullRandom() {
        populateQueuesRandomly();
        assertPriorityPollResults();
    }

    @Test
    void testGetByPriorityRepetitionRandom() {
        populateQueuesRandomlyWithRepetition();
        assertPriorityPollResults();
    }

    @Test
    void testGetByCreationTimeFullRandom() {
        populateQueuesRandomly();
        assertOrderedPollResults();
    }

    @Test
    void testGetByCreationTimeRepetitionRandom() {
        populateQueuesRandomlyWithRepetition();
        assertOrderedPollResults();
    }

    @Test
    void testGetByPriorityAndByCreationTimeFullRandom() {
        populateQueuesRandomly();
        assertPriorityAndOrderedPollResultsRandomly();
    }

    @Test
    void testGetByPriorityAndByCreationTimeRepetitionRandom() {
        populateQueuesRandomlyWithRepetition();
        assertPriorityAndOrderedPollResultsRandomly();
    }

    @Test
    void testStressGetByPriorityAndByCreationTimeRandom() {
        for (int i = 0; i < NUM_OF_STRESS_TESTS; i++) {
            populateQueuesRandomlyForStress();
            assertPriorityAndOrderedPollResultsRandomly();
        }
    }
}

class ManagerPatientTest {
    static final int NUM_OF_PATIENTS = 5000;
    static final int NUM_OF_REPETITION = 10;
    static final int NUM_OF_PATIENTS_FOR_STRESS = 500;
    static final int NUM_OF_STRESS_TESTS = 100;
    static final Random random = new Random();

    java.util.Queue<Patient> builtinOrderedQueue;
    java.util.Queue<Patient> builtinPriorityQueue;
    Manager<Patient> manager;


    @BeforeEach
    void setUp() {
        builtinOrderedQueue = new java.util.LinkedList<>();
        builtinPriorityQueue = new java.util.PriorityQueue<>(Collections.reverseOrder());
        manager = new Manager<>();
    }

    @AfterEach
    void tearDown() {
        builtinOrderedQueue = null;
        builtinPriorityQueue = null;
        manager = null;
    }

    void populateQueues() {
        for (int i = 0; i < NUM_OF_PATIENTS; i++) {
            Patient p = new Patient(i, false);
            manager.add(p);
            builtinPriorityQueue.add(p);
            builtinOrderedQueue.add(p);
        }
    }

    void populateQueuesRandomly() {
        populateQueuesRandomly(NUM_OF_PATIENTS);
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
            Patient p = new Patient(
                    random.nextInt(upperBound),
                    random.nextBoolean());
            manager.add(p);
            builtinPriorityQueue.add(p);
            builtinOrderedQueue.add(p);
        }
    }

    void assertPriorityPollResults() {
        while (!builtinPriorityQueue.isEmpty()) {
            Patient expectedPatient = builtinPriorityQueue.poll();
            Patient actualPatient = manager.getByPriority();
            assertEquals(expectedPatient, actualPatient);
        }
        assertNull(manager.getByPriority());
    }

    void assertOrderedPollResults() {
        while (!builtinOrderedQueue.isEmpty()) {
            Patient expectedPatient = builtinOrderedQueue.poll();
            Patient actualPatient = manager.getByCreationTime();
            assertEquals(expectedPatient, actualPatient);
        }
        assertNull(manager.getByCreationTime());
    }

    void assertPriorityAndOrderedPollResults() {
        while (!builtinPriorityQueue.isEmpty() && !builtinOrderedQueue.isEmpty()) {
            Patient expectedPatient = builtinPriorityQueue.poll();
            builtinOrderedQueue.remove(expectedPatient);
            Patient actualPatient = manager.getByPriority();
            assertEquals(expectedPatient, actualPatient);
            expectedPatient = builtinOrderedQueue.poll();
            builtinPriorityQueue.remove(expectedPatient);
            actualPatient = manager.getByCreationTime();
            assertEquals(expectedPatient, actualPatient);

        }
        assertNull(manager.getByPriority());
        assertNull(manager.getByCreationTime());
    }

    void assertPriorityAndOrderedPollResultsRandomly() {
        while (!builtinPriorityQueue.isEmpty() && !builtinOrderedQueue.isEmpty()) {
            Patient expectedPatient;
            Patient actualPatient;
            if (random.nextBoolean()) {
                expectedPatient = builtinPriorityQueue.poll();
                builtinOrderedQueue.remove(expectedPatient);
                actualPatient = manager.getByPriority();
            } else {
                expectedPatient = builtinOrderedQueue.poll();
                builtinPriorityQueue.remove(expectedPatient);
                actualPatient = manager.getByCreationTime();
            }
            assertEquals(expectedPatient, actualPatient);
        }
        assertNull(manager.getByPriority());
        assertNull(manager.getByCreationTime());
    }

    @Test
    void testGetByPriority() {
        populateQueues();
        assertPriorityPollResults();
    }

    @Test
    void testGetByCreationTime() {
        populateQueues();
        assertOrderedPollResults();
    }

    @Test
    void testGetByPriorityAndByCreationTime() {
        populateQueues();
        assertPriorityAndOrderedPollResults();
    }

    @Test
    void testGetByPriorityFullRandom() {
        populateQueuesRandomly();
        assertPriorityPollResults();
    }

    @Test
    void testGetByPriorityRepetitionRandom() {
        populateQueuesRandomlyWithRepetition();
        assertPriorityPollResults();
    }

    @Test
    void testGetByCreationTimeFullRandom() {
        populateQueuesRandomly();
        assertOrderedPollResults();
    }

    @Test
    void testGetByCreationTimeRepetitionRandom() {
        populateQueuesRandomlyWithRepetition();
        assertOrderedPollResults();
    }

    @Test
    void testGetByPriorityAndByCreationTimeFullRandom() {
        populateQueuesRandomly();
        assertPriorityAndOrderedPollResultsRandomly();
    }

    @Test
    void testGetByPriorityAndByCreationTimeRepetitionRandom() {
        populateQueuesRandomlyWithRepetition();
        assertPriorityAndOrderedPollResultsRandomly();
    }

    @Test
    void testStressGetByPriorityAndByCreationTimeRandom() {
        for (int i = 0; i < NUM_OF_STRESS_TESTS; i++) {
            populateQueuesRandomlyForStress();
            assertPriorityAndOrderedPollResultsRandomly();
        }
    }
}
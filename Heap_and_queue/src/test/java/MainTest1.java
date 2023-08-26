//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.util.Collections;
//import java.util.Random;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//
//class MainTest1 {
//    static final int NUM_OF_PATIENTS = 20000;
//    static final int NUM_OF_REPETITION = 10;
//    static final int NUM_OF_PATIENTS_FOR_STRESS = 250;
//    static final int NUM_OF_STRESS_TESTS = 100;
//    static final Random random = new Random();
//
//    java.util.Queue<Patient> builtinOrderedQueue;
//    java.util.Queue<Patient> builtinPriorityQueue;
//    Manager<Patient> manager;
//    ByteArrayOutputStream outputStream;
//    StringBuilder priorityQueueOutput;
//    StringBuilder orderedQueueOutput;
//
//
//    @BeforeEach
//    void setUp() {
//        builtinOrderedQueue = new java.util.LinkedList<>();
//        builtinPriorityQueue = new java.util.PriorityQueue<>(Collections.reverseOrder());
//        manager = new Manager<>();
//        outputStream = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outputStream));
//        priorityQueueOutput = new StringBuilder();
//        orderedQueueOutput = new StringBuilder();
//    }
//
//    @AfterEach
//    void tearDown() {
//        builtinOrderedQueue = null;
//        builtinPriorityQueue = null;
//        manager = null;
//        outputStream = null;
//        priorityQueueOutput = null;
//        orderedQueueOutput = null;
//    }
//
//    private static void addOutput(StringBuilder sb, Patient p) {
//        sb.append(p).append("\n");
//    }
//
//    void populateQueues() {
//        for (int i = 0; i < NUM_OF_PATIENTS; i++) {
//            Patient p = new Patient(i, false);
//            manager.add(p);
//            builtinPriorityQueue.add(p);
//            builtinOrderedQueue.add(p);
//            addOutput(orderedQueueOutput, p);
//        }
//        addPriorityOutput();
//    }
//
//    private void addAllOutput(Manager<Patient> manager2) {
//        Patient p = manager2.getByCreationTime();
//        while (p != null) {
//            builtinPriorityQueue.add(p);
//            builtinOrderedQueue.add(p);
//            addOutput(orderedQueueOutput, p);
//            manager.add(p);
//            p = manager2.getByCreationTime();
//        }
//        addPriorityOutput();
//    }
//
//    private void addPriorityOutput() {
//        while (!builtinPriorityQueue.isEmpty()) {
//            Patient expectedPatient = builtinPriorityQueue.poll();
//            addOutput(priorityQueueOutput, expectedPatient);
//        }
//    }
//
//    void populateQueuesRandomly() {
//        populateQueuesRandomly(NUM_OF_PATIENTS);
//    }
//
//    void populateQueuesRandomlyWithRepetition() {
//        populateQueuesRandomly(NUM_OF_REPETITION);
//    }
//
//    void populateQueuesRandomlyForStress() {
//        populateQueuesRandomly(NUM_OF_PATIENTS_FOR_STRESS,
//                NUM_OF_PATIENTS_FOR_STRESS);
//    }
//
//    void populateQueuesRandomly(int upperBound) {
//        populateQueuesRandomly(NUM_OF_PATIENTS, upperBound);
//    }
//
//    void populateQueuesRandomly(int size, int upperBound) {
//        for (int i = 0; i < size; i++) {
//            Patient p = new Patient(
//                    random.nextInt(upperBound),
//                    random.nextBoolean());
//            manager.add(p);
//            builtinPriorityQueue.add(p);
//            builtinOrderedQueue.add(p);
//            addOutput(orderedQueueOutput, p);
//        }
//        addPriorityOutput();
//    }
//
//    void assertPriorityPollResults() {
//        Main.simulateOnlyByPriority(manager);
//        assertEquals(priorityQueueOutput.toString(), outputStream.toString());
//        assertNull(manager.getByPriority());
//    }
//
//    void assertOrderedPollResults() {
//        Main.simulateOnlyByCreation(manager);
//        assertEquals(orderedQueueOutput.toString(), outputStream.toString());
//        assertNull(manager.getByCreationTime());
//    }
//
//    void assertPriorityPollResultsWithEnqueue() {
//        Manager<Patient> manager2 = new Manager<>();
//        Main.simulateEnqueueTimesSeparation(manager2);
//        addAllOutput(manager2);
//        assertPriorityPollResults();
//    }
//
//    void assertOrderedPollResultsWithEnqueue() {
//        Manager<Patient> manager2 = new Manager<>();
//        Main.simulateEnqueueTimesSeparation(manager2);
//        addAllOutput(manager2);
//        assertOrderedPollResults();
//    }
//
//    @Test
//    void testSimulateOnlyByPriority() {
//        populateQueues();
//        assertPriorityPollResults();
//    }
//
//    @Test
//    void testSimulateOnlyByCreation() {
//        populateQueues();
//        assertOrderedPollResults();
//    }
//
//    @Test
//    void testSimulateOnlyByPriorityWithSimulateEnqueueTimesSeparation() {
//        assertPriorityPollResultsWithEnqueue();
//    }
//
//    @Test
//    void testSimulateOnlyByCreationWithSimulateEnqueueTimesSeparation() {
//        assertOrderedPollResultsWithEnqueue();
//    }
//
//    @Test
//    void testSimulateOnlyByPriorityFullRandom() {
//        populateQueuesRandomly();
//        assertPriorityPollResults();
//    }
//
//    @Test
//    void testSimulateOnlyByPriorityRepetitionRandom() {
//        populateQueuesRandomlyWithRepetition();
//        assertPriorityPollResults();
//    }
//
//    @Test
//    void testSimulateOnlyByCreationFullRandom() {
//        populateQueuesRandomly();
//        assertOrderedPollResults();
//    }
//
//
//    @Test
//    void testStressSimulateOnlyByPriorityFullRandom() {
//        for (int i = 0; i < NUM_OF_STRESS_TESTS; i++) {
//            populateQueuesRandomlyForStress();
//            assertPriorityPollResults();
//        }
//    }
//
//    @Test
//    void testStressSimulateOnlyByCreationFullRandom() {
//        for (int i = 0; i < NUM_OF_STRESS_TESTS; i++) {
//            populateQueuesRandomlyForStress();
//            assertOrderedPollResults();
//        }
//    }
//}

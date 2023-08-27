import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class quickSortRecitationTest {
    Sort s = new Sort();
    testHelper helper = new testHelper();
    int size = 5000;

    @Test
    void testRandom() {
        Integer[]a = new Integer[size];
        helper.randomizeArray(a);
        s.quickSortRecitation(a);
        assertEquals(true, helper.isSortedArray(a));
    }

    @Test
    void testSorted() {
        Integer[]a = new Integer[size];
        helper.sortArray(a);
        s.quickSortRecitation(a);
        assertEquals(true, helper.isSortedArray(a));
    }

    @Test
    void testSortedOpposite() {
        Integer[]a = new Integer[size];
        helper.sortOpposite(a);
        s.quickSortRecitation(a);
        assertEquals(true, helper.isSortedArray(a));
    }
}

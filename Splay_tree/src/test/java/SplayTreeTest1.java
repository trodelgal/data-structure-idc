import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SplayTreeTest1 {
    // Helpers
    public static String reverseSubString(String s, int from, int to) {
        StringBuilder sb = new StringBuilder(s.substring(from, to + 1));
        sb.reverse();
        return s.substring(0, from) + sb + s.substring(to + 1);
    }

    public static String substituteChar(String s, int loc, char c) {
        StringBuilder sb = new StringBuilder(s);
        sb.replace(loc, loc+1, "" + c);
        return sb.toString();
    }

    public static List<String> generateDNAList(int max_size, int min_size) {
        List<String> dnaList = new ArrayList<>();

        for (int i = min_size; i < max_size; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                char randomChar = (char) ('A' + (int) (Math.random() * ((float) ('Z' - 'A'))));
                sb.append(randomChar);
            }
            dnaList.add(sb.toString());
        }

        return dnaList;
    }

    public static List<String> generateDNAList(int max_size) {
        return generateDNAList(max_size, 1);
    }

    public static String generateDNA(int size) {
        return generateDNAList(size+1, size).get(0);
    }

    /**
     * For each node in the given DNA, set swap to 0 or 1
     * According to the binary representation binRep
     */
    public static SplayTree getDNABySwaps(String dna, String binRep) {
        int dnaSize = dna.length();
        List<SplayTree.Node> nodeList = new ArrayList<>();
        SplayTree st = new SplayTree(dna);
        for (int n = 0; n<dnaSize; n++) {
            nodeList.add(st.find(n));
        }
        for (int j = 0; j<dnaSize; j++) {
            if (binRep.length() > j) {
                nodeList.get(j).swap = (binRep.charAt(j) == '1' ? 1:0);
            } else {
                nodeList.get(j).swap = 0;
            }
        }
        return st;
    }

    /**
     * Get all possible swap patterns in string binary representation
     */
    public static List<String> getSwapBinReps(String dna) {
        List<String> binReps = new ArrayList<>();
        for (int i = 0; i<(int) Math.pow(2,dna.length()); i++) {
            binReps.add(Integer.toString(i, 2));
        }
        return binReps;
    }

    // Params
    private static Stream<Arguments> invertParams() {
        List<Arguments> argList = new ArrayList<>();
        for (String dna: generateDNAList(10)) {
            for (String binRep: getSwapBinReps(dna)) {
                for (int i = 0; i<dna.length(); i++) {
                    for (int j = dna.length(); j>i; j--) {
                        argList.add(arguments(dna, binRep, i, j));
                    }
                }
            }
        }

        return argList.stream();
    }

    private static Stream<Arguments> translocateParams() {
        List<Arguments> argList = new ArrayList<>();
        for (String dna: generateDNAList(7)) {
            for (String binRep: getSwapBinReps(dna)) {
                for (int i = 0; i<dna.length(); i++) {
                    for (int j = dna.length() - 1; j>i; j--) {
                        for (int splay = 0; splay<dna.length(); splay++){
                            for (int k = 0; k < i; k++) {
                                argList.add(arguments(dna, binRep, i, j, k, splay));
                            }
                            for (int k = dna.length(); k > j; k--) {
                                argList.add(arguments(dna, binRep, i, j, k, splay));
                            }
                        }
                    }
                }
            }
        }

        return argList.stream();
    }

    private static Stream<Arguments> splayParams() {
        List<Arguments> argList = new ArrayList<>();

        int dnaSize = 10;

        String dna = generateDNA(dnaSize);
        List<String> binReps = getSwapBinReps(dna);
        for (String binRep: binReps) {
            for (int i = 0; i<dnaSize; i++) {
                argList.add(arguments(dna, binRep, i));
            }
        }

        return argList.stream();
    }

    private static Stream<Arguments> insertParams() {
        List<Arguments> argList = new ArrayList<>();

        for (String dna: generateDNAList(5)) {
            List<String> binReps = getSwapBinReps(dna);
            for (String binRep : binReps) {
                for (int i = 0; i < dna.length(); i++) {
                    for (int j = 0; j < dna.length(); j++) {
                        argList.add(arguments(dna, binRep, i, j));
                    }
                }
            }
        }

        return argList.stream();
    }

    private static Stream<Arguments> deleteParams() {
        List<Arguments> argList = new ArrayList<>();

        for (String dna: generateDNAList(5)) {
            List<String> binReps = getSwapBinReps(dna);
            for (String binRep : binReps) {
                for (int i = 0; i < dna.length(); i++) {
                    for (int j = 0; j < dna.length(); j++) {
                        argList.add(arguments(dna, binRep, i, j));
                    }
                }
            }
        }

        return argList.stream();
    }

    private static Stream<Arguments> splitParams() {
        List<Arguments> argList = new ArrayList<>();

        for (String dna: generateDNAList(10)) {
            List<String> binReps = getSwapBinReps(dna);
            for (String binRep: binReps) {
                for (int i = 0; i<dna.length(); i++) {
                    for (int j = 0; j<dna.length(); j++) {
                        argList.add(arguments(dna, binRep, i, j));
                    }
                }
            }

        }

        return argList.stream();
    }

    private static Stream<Arguments> joinParams() {
        List<Arguments> argList = new ArrayList<>();

        for (String dna1: generateDNAList(5)) {
            for (String dna2: generateDNAList(5)) {
                List<String> binReps1 = getSwapBinReps(dna1);
                List<String> binReps2 = getSwapBinReps(dna2);
                for (String binRep1 : binReps1) {
                    for (String binRep2 : binReps2) {
                        for (int i = 0; i < dna1.length(); i++) {
                            for (int j = 0; j < dna2.length(); j++) {
                                argList.add(arguments(dna1, binRep1, dna2, binRep2, i, j));
                            }
                        }
                    }
                }
            }

        }

        return argList.stream();
    }

    private static Stream<Arguments> substituteParams() {
        List<Arguments> argList = new ArrayList<>();

        int dnaSize = 10;
        String dna = generateDNA(dnaSize);
        List<String> binReps = getSwapBinReps(dna);
        for (String binRep: binReps) {
            for (int i = 0; i<dnaSize; i++) {
                for (int j = 0; j<dnaSize; j++) {
                    argList.add(arguments(dna, binRep, i, '!', j));
                }
            }
        }

        return argList.stream();
    }

    private static Stream<Arguments> findParams() {
        String dna = generateDNA(10);
        List<Arguments> argList = new ArrayList<>();

        for (int i = 0; i<dna.length(); i++) {
            argList.add(arguments(dna, i));
        }

        return argList.stream();
    }

    private static Stream<Arguments> invalidInvertParameters() {
        String dna = generateDNA(10);
        return Stream.of(
            arguments(0, dna.length() + 1, dna),
            arguments(-1, dna.length(), dna),
            arguments(5, 0, dna)
        );
    }

    // Tests
    @Test
    //@passed
    public void testToString() {
        SplayTree st = new SplayTree("ABCDEF");

        // Checking initial string
        assertEquals("ABCDEF", st.toString(st.root));

        // After some operations
        st.translocate(1, 2, 4); // ABCDEF -> ADBCEF
        assertEquals("ADBCEF", st.toString(st.root));

        st.insert(3, 'Z'); // ADBCEF -> ADBCZEF
        assertEquals("ADBZCEF", st.toString(st.root));

        st.delete(4); // ADBCZEF -> ADBCEF
        assertEquals("ADBZEF", st.toString(st.root));
    }

    @ParameterizedTest(name="Generating dna ''{0}'' with binRep ''{1}''. Substituting loc ''{2}'' with char: ''{3}''")
    @MethodSource("substituteParams")
    //passed
    public void testSubstitute(
        String dna,
        String binRep,
        int k,
        char c,
        int splay
    ) {
        SplayTree st = getDNABySwaps(dna, binRep);

        dna = st.toString();

        st.select(splay);
        st.substitute(k, c);

        assertEquals(substituteChar(dna, k, c), st.toString());
    }

    @ParameterizedTest(name="Inverting dna ''{0}'' and splaying k=''{1}''")
    @MethodSource("findParams")
    //passed
    public void testFind(
        String dna,
        int k
    ) {
        SplayTree st = new SplayTree(dna);

        st.root.swap = 1;

        assertEquals(reverseSubString(dna,0, dna.length() - 1).charAt(k), st.find(k).character);

    }

    @ParameterizedTest(name="Inverting dna ''{0}'' with swap pattern ''{1}'' and splaying k=''{2}''")
    @MethodSource("splayParams")
    //passed
    public void testSplay(
        String dna,
        String binRep,
        int k
    ) {
        SplayTree st = getDNABySwaps(dna, binRep);

        dna = st.toString();

        st.select(k);
        assertEquals(dna, st.toString());
    }

    @ParameterizedTest(name="Splitting dna ''{0}'' with swap pattern ''{1}'' at ''{2}''")
    @MethodSource("splitParams")
    //fixed
    public void testSplit(
        String dna,
        String binRep,
        int k,
        int splay
    ) {
       SplayTree st = getDNABySwaps(dna, binRep);

       dna = st.toString();

       st.select(splay);

       SplayTree[] SplitST = st.split(st.select(k));
       SplayTree T1 = SplitST[0]; SplayTree T2 = SplitST[1];
       assertEquals(T1.toString(), dna.substring(0,k));
       assertEquals(T2.toString(), dna.substring(k));
    }

    @ParameterizedTest(name="Joining dna ''{0}'' with swap pattern ''{1}'' and dna ''{2}'' with swap pattern ''{3}''")
    @MethodSource("joinParams")
    //fixed
    public void testJoin(
        String dna1,
        String binRep1,
        String dna2,
        String binRep2,
        int splay1,
        int splay2
    ) {
        SplayTree T1 = getDNABySwaps(dna1, binRep1);
        SplayTree T2 = getDNABySwaps(dna2, binRep2);

        dna1 = T1.toString(); dna2 = T2.toString();

        T1.select(splay1);
        T2.select(splay2);

        SplayTree st = new SplayTree(T1, T2);

        assertEquals(dna1 + dna2, st.toString());
    }

    @ParameterizedTest(name="Inverting dna ''{0}'' with swap pattern ''{1}'' from ''{2}'' to ''{3}''")
    @MethodSource("invertParams")
    //todo
    public void testInvert(
        String dna,
        String binRep,
        int invertFrom,
        int invertTo
    ) {
        if (invertTo >= dna.length())
            return;
        SplayTree st = getDNABySwaps(dna, binRep);

        dna = st.toString();
        String expectedDna = reverseSubString(dna, invertFrom, invertTo);

        st.invert(invertFrom, invertTo);

        assertEquals(expectedDna, st.toString());
    }

    /*
    @ParameterizedTest(name="Inverting dna ''{2}'' from ''{0}'' to ''{1}'' -> ''{2}''")
    @MethodSource("invalidInvertParameters")
    //done like in the piazza

    public void testInvert_InvalidInput(
        int invertFrom,
        int invertTo,
        String dna
    ) {
        SplayTree st = new SplayTree(dna);

        st.invert(invertFrom, invertTo);

        assertEquals(dna, st.toString());
    }
     */

    @ParameterizedTest(name="Inserting to dna ''{0}'' with swap pattern ''{1}'' in loc ''{2}''. splaying: ''{3}''")
    @MethodSource("insertParams")
    //fixed
    public void testInsert(
        String dna,
        String binRep,
        int i,
        int splay
    ) {
        SplayTree st = getDNABySwaps(dna, binRep);

        st.select(splay);

        dna = st.toString();

        st.insert(i, 'T');
        assertEquals(dna.substring(0, i) + "T" + dna.substring(i), st.toString());
    }


    @ParameterizedTest(name="Deleting from dna ''{0}'' with swap pattern ''{1}'' in loc ''{2}''. splaying: ''{3}''")
    @MethodSource("deleteParams")
    //fixed
    public void testDelete(
        String dna,
        String binRep,
        int i,
        int splay
    ) {
        SplayTree st = getDNABySwaps(dna, binRep);

        st.select(splay);

        dna = st.toString();

        st.delete(i);
        assertEquals(dna.substring(0, i) + dna.substring(i+1), st.toString());
    }

    @ParameterizedTest(name="Deleting from dna ''{0}'' with swap pattern ''{1}'' in loc ''{2}''. splaying: ''{3}''")
    @MethodSource("translocateParams")
    //fixed
    public void testTranslocate(
        String dna,
        String binRep,
        int i,
        int j,
        int k,
        int splay
    ) {
        SplayTree st = getDNABySwaps(dna, binRep);
        dna = st.toString();

        st.select(splay);

        st.translocate(i, j, k);
        String expectedDNA;
        if (k < i) {
            expectedDNA = dna.substring(0,k) + dna.substring(i,j+1) + dna.substring(k, i) + dna.substring(j+1);
        } else { // k > j
            expectedDNA = dna.substring(0,i) + dna.substring(j+1,k) + dna.substring(i, j+1) + dna.substring(k);
        }
        assertEquals(expectedDNA, st.toString(st.root));
    }

}

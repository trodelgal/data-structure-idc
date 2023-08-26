import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UniqueRandom {
    private final Set<Integer> generatedNumbers = new HashSet<>();
    private final int upperBound;
    static final Random random = new Random();

    public UniqueRandom(int upperBound) {
        this.upperBound = upperBound;
    }

    public int nextInt() {
        if (generatedNumbers.size() == upperBound)
            throw new IllegalStateException("All unique integers within the specified range have been generated.");
        int randomNumber;
        do {
            randomNumber = random.nextInt(upperBound);
        } while (!generatedNumbers.add(randomNumber));
        return randomNumber;
    }
}
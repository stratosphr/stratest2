package utilities.maths;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by gvoiron on 22/09/17.
 * Time : 14:45
 */
public final class Maths {

    public static List<Integer> range(int lowerBound, int upperBound) {
        if (lowerBound >= upperBound) {
            throw new Error("Invalid range " + lowerBound + ".." + upperBound + " (the lower bound must be lower than the upper bound).");
        }
        return IntStream.rangeClosed(lowerBound, upperBound).boxed().collect(Collectors.toList());
    }

}

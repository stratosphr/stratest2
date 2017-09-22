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
        return IntStream.rangeClosed(lowerBound, upperBound).boxed().collect(Collectors.toList());
    }

}

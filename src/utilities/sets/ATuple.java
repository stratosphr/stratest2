package utilities.sets;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 15:57
 */
public abstract class ATuple {

    private final List<Object> elements;

    public ATuple(Object... elements) {
        this.elements = Arrays.asList(elements);
    }

    @Override
    public String toString() {
        return "(" + elements.stream().map(Object::toString).collect(Collectors.joining(", ")) + ")";
    }

}

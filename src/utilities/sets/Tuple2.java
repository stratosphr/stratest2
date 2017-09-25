package utilities.sets;

/**
 * Created by gvoiron on 21/09/17.
 * Time : 10:41
 */
public final class Tuple2<T1, T2> extends ATuple {

    private final T1 first;
    private final T2 second;

    public Tuple2(T1 first, T2 second) {
        super(first, second);
        this.first = first;
        this.second = second;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

}

package utilities.sets;

/**
 * Created by gvoiron on 21/09/17.
 * Time : 10:41
 */
public final class Tuple3<T1, T2, T3> extends ATuple {

    private final T1 first;
    private final T2 second;
    private final T3 third;

    public Tuple3(T1 first, T2 second, T3 third) {
        super(first, second, third);
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

    public T3 getThird() {
        return third;
    }

}

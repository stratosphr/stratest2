package algs;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 15:48
 */
public abstract class AComputer<T> {

    public final T compute() {
        return run();
    }

    protected abstract T run();

}

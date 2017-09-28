package algs;

import algs.outputs.ComputerResult;
import utilities.Time;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 15:48
 */
public abstract class AComputer<T> {

    public final ComputerResult<T> compute() {
        preRun();
        long start = Time.now();
        T computed = run();
        long end = Time.now();
        postRun();
        return new ComputerResult<>(computed, new Time(end - start));
    }

    protected void preRun() {
    }

    protected abstract T run();

    protected void postRun() {
    }

}

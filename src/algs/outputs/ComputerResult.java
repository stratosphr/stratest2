package algs.outputs;

import utilities.Time;

/**
 * Created by gvoiron on 18/06/17.
 * Time : 15:59
 */

public final class ComputerResult<T> {

    private final T computed;
    private final Time time;

    public ComputerResult(T computed, Time time) {
        this.computed = computed;
        this.time = time;
    }

    public T getResult() {
        return computed;
    }

    public Time getComputationTime() {
        return time;
    }

}

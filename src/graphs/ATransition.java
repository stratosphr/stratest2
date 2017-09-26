package graphs;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 16:08
 */
public abstract class ATransition<S, L> {

    protected final S source;
    protected final L label;
    protected final S target;

    public ATransition(S source, L label, S target) {
        this.source = source;
        this.label = label;
        this.target = target;
    }

    public S getSource() {
        return source;
    }

    public L getLabel() {
        return label;
    }

    public S getTarget() {
        return target;
    }

    @Override
    public final int hashCode() {
        return toString().hashCode();
    }

    @Override
    public final boolean equals(Object o) {
        return getClass().getName().equals(o.getClass().getName()) && toString().equals(o.toString());
    }

    @Override
    public abstract String toString();

}

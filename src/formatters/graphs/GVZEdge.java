package formatters.graphs;

import java.util.stream.Collectors;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 13:50
 */
public final class GVZEdge extends AGVZNode<GVZEdge> {

    private final GVZState source;
    private final GVZState target;

    public GVZEdge(GVZState source, GVZState target) {
        this.source = source;
        this.target = target;
    }

    @Override
    protected GVZEdge getThis() {
        return this;
    }

    public GVZState getSource() {
        return source;
    }

    public GVZState getTarget() {
        return target;
    }

    public GVZEdge setHeadLabel(Object headLabel) {
        return setHeadLabel(headLabel, false);
    }

    public GVZEdge setHeadLabel(Object headLabel, boolean isHTML) {
        return isHTML ? htmlParameter("headlabel", headLabel) : quoteParameter("headlabel", headLabel);
    }

    public GVZEdge setTailLabel(Object tailLabel) {
        return setHeadLabel(tailLabel, false);
    }

    public GVZEdge setTailLabel(Object tailLabel, boolean isHTML) {
        return isHTML ? htmlParameter("taillabel", tailLabel) : quoteParameter("taillabel", tailLabel);
    }

    @Override
    public String toString() {
        return getSource().getName() + " -> " + getTarget().getName() + "[" + parameters.keySet().stream().map(key -> key + "=" + parameters.get(key)).collect(Collectors.joining(", ")) + "]";
    }

}

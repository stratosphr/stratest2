package formatters.graphs;

import java.util.stream.Collectors;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 13:48
 */
public final class GVZState extends AGVZNode<GVZState> {

    private String name;

    public GVZState(String name) {
        this.name = name;
    }

    @Override
    protected GVZState getThis() {
        return this;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "[" + parameters.keySet().stream().map(key -> key + "=" + parameters.get(key)).collect(Collectors.joining(", ")) + "]" + (comment.isEmpty() ? "" : " // " + comment);
    }

}

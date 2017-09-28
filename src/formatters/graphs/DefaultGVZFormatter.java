package formatters.graphs;

import graphs.AState;
import graphs.ATransition;
import langs.eventb.Event;

import java.util.stream.Collectors;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 13:05
 */
public final class DefaultGVZFormatter<S extends AState<?, ?>> extends AGVZFormatter<S, Event> {

    public DefaultGVZFormatter(boolean useFullLabels, ERankDir rankDir) {
        super(useFullLabels, rankDir);
    }

    @Override
    public GVZState formatInitialState(S initialState) {
        GVZState invisible = new GVZState("__invisible__").setShape("point").setColor("forestgreen");
        GVZState initial = formatReachedState(initialState).setPenWidth(3).setComment("Initial");
        states.add(invisible);
        edges.add(new GVZEdge(invisible, initial).setColor("forestgreen"));
        return initial;
    }

    @Override
    public GVZState formatReachedState(S reachedState) {
        String markers = reachedState.getMarkers().keySet().stream().map(key -> key + "=" + reachedState.getMarkers().get(key)).collect(Collectors.joining());
        return new GVZState(reachedState.getName()).setLabel(useFullLabels ? reachedState + (markers.isEmpty() ? "" : "\\n" + markers) : reachedState.getName() + (markers.isEmpty() ? "" : "\\n" + markers)).setShape("box").setStyle("rounded, filled").setColor("forestgreen").setFillColor("limegreen").setColor("forestgreen");
    }

    @Override
    public GVZState formatUnreachedState(S unreachedState) {
        return formatReachedState(unreachedState).setFillColor("lightblue2").setColor("deepskyblue4");
    }

    @Override
    public GVZEdge formatReachedTransition(ATransition<S, Event> reachedTransition) {
        return new GVZEdge(new GVZState(reachedTransition.getSource().getName()), new GVZState(reachedTransition.getTarget().getName())).setLabel(reachedTransition.getLabel().getName()).setColor("forestgreen");
    }

    @Override
    public GVZEdge formatUnreachedTransition(ATransition<S, Event> unreachedTransition) {
        return formatReachedTransition(unreachedTransition).setColor("black").setStyle("dashed");
    }

}

package formatters.graphs;

import algs.ReachablePartComputer;
import formatters.AFormatter;
import graphs.AFSM;
import graphs.AState;
import graphs.ATransition;
import utilities.ICloneable;
import utilities.sets.Tuple2;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 12:41
 */
public abstract class AGVZFormatter<S extends AState, L extends ICloneable> extends AFormatter implements IGVZFormatter<S, L> {

    protected final boolean useFullLabels;
    protected final ERankDir rankDir;
    protected final List<GVZState> states;
    protected final List<GVZEdge> edges;

    protected AGVZFormatter(boolean useFullLabels, ERankDir rankDir) {
        this.useFullLabels = useFullLabels;
        this.rankDir = rankDir;
        this.states = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    @Override
    public String visit(AFSM<S, L> fsm) {
        Tuple2<LinkedHashSet<S>, ArrayList<ATransition<S, L>>> rchblPart = new ReachablePartComputer<>(fsm).compute().getResult();
        fsm.getInitialStates().forEach(state -> states.add(formatInitialState(state)));
        rchblPart.getFirst().stream().filter(state -> !fsm.getInitialStates().contains(state)).forEach(state -> states.add(formatReachedState(state)));
        fsm.getStates().stream().filter(state -> !rchblPart.getFirst().contains(state)).forEach(state -> states.add(formatUnreachedState(state)));
        rchblPart.getSecond().forEach(transition -> edges.add(formatReachedTransition(transition)));
        fsm.getTransitions().stream().filter(transition -> !rchblPart.getSecond().contains(transition)).forEach(transition -> edges.add(formatUnreachedTransition(transition)));
        return line("digraph g {") + line() + indentRight() + indentLine("rankdir=\"" + rankDir + "\"") + line() + states.stream().map(state -> indentLine(state.toString())).collect(Collectors.joining()) + line() + edges.stream().map(edge -> indentLine(edge.toString())).collect(Collectors.joining()) + line() + indentLeft() + indentLine("}");
    }

    public abstract GVZState formatInitialState(S initialState);

    public abstract GVZState formatReachedState(S reachedState);

    public abstract GVZState formatUnreachedState(S unreachedState);

    public abstract GVZEdge formatReachedTransition(ATransition<S, L> reachedTransition);

    public abstract GVZEdge formatUnreachedTransition(ATransition<S, L> unreachedTransition);

}

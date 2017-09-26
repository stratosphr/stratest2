package graphs;

import formatters.graphs.IGVZFormattable;
import formatters.graphs.IGVZFormatter;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 15:54
 */
public abstract class AFSM<S extends AState, L> implements IGVZFormattable<S, L> {

    private final LinkedHashSet<S> initialStates;
    private final LinkedHashSet<S> states;
    private final LinkedHashSet<? extends ATransition<S, L>> transitions;

    public AFSM(LinkedHashSet<S> initialStates, LinkedHashSet<S> states, LinkedHashSet<? extends ATransition<S, L>> transitions) {
        this.initialStates = initialStates;
        this.states = states;
        this.transitions = transitions;
    }

    @Override
    public final LinkedHashSet<S> getInitialStates() {
        return initialStates;
    }

    @Override
    public final LinkedHashSet<S> getStates() {
        return states;
    }

    @Override
    public final LinkedHashSet<? extends ATransition<S, L>> getTransitions() {
        return transitions;
    }

    @Override
    public String accept(IGVZFormatter<S, L> formatter) {
        return formatter.visit(this);
    }

}

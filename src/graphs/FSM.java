package graphs;

import formatters.graphs.IGVZFormatter;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 15:54
 */
public final class FSM<S extends AState, L> extends AFSM<S, L> {

    public FSM(LinkedHashSet<S> initialStates, LinkedHashSet<S> states, LinkedHashSet<? extends ATransition<S, L>> transitions) {
        super(initialStates, states, transitions);
    }

    @Override
    public String accept(IGVZFormatter<S, L> formatter) {
        return formatter.visit(this);
    }

}

package graphs;

import formatters.graphs.IGVZFormatter;
import utilities.ICloneable;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 15:54
 */
public final class FSM<S extends AState, L extends ICloneable> extends AFSM<S, L> {

    public FSM(LinkedHashSet<S> initialStates, LinkedHashSet<S> states, LinkedHashSet<? extends ATransition<S, L>> transitions) {
        super(initialStates, states, transitions);
    }

    @Override
    public String accept(IGVZFormatter<S, L> formatter) {
        return formatter.visit(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public FSM<S, L> clone() {
        return new FSM<>(new LinkedHashSet<>(initialStates.stream().map(s -> (S) s.clone()).collect(Collectors.toList())), null, null);
    }

}

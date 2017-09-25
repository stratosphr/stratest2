package graphs;

import utilities.sets.Tuple3;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 15:54
 */
public final class FSM<S extends AState, T extends ATransition> {

    private final LinkedHashSet<S> initialStates;
    private final LinkedHashSet<S> states;
    private final LinkedHashSet<T> transitions;

    public FSM(LinkedHashSet<S> initialStates, LinkedHashSet<S> states, LinkedHashSet<T> transitions) {
        this.initialStates = initialStates;
        this.states = states;
        this.transitions = transitions;
    }

    public LinkedHashSet<S> getInitialStates() {
        return initialStates;
    }

    public LinkedHashSet<S> getStates() {
        return states;
    }

    public LinkedHashSet<T> getTransitions() {
        return transitions;
    }

    @Override
    public String toString() {
        return new Tuple3<>(initialStates, states, transitions).toString();
    }

}

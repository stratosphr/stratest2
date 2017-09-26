package algs;

import graphs.AFSM;
import graphs.AState;
import graphs.ATransition;
import utilities.sets.Tuple2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 13:26
 */
public final class ReachablePartComputer<S extends AState, L> extends AComputer<Tuple2<LinkedHashSet<S>, ArrayList<ATransition<S, L>>>> {

    private AFSM<S, L> fsm;
    private final LinkedHashMap<S, ArrayList<ATransition<S, L>>> adjacency;

    public ReachablePartComputer(AFSM<S, L> fsm) {
        this.fsm = fsm;
        this.adjacency = new LinkedHashMap<>();
        fsm.getStates().forEach(s -> adjacency.put(s, new ArrayList<>()));
        fsm.getTransitions().forEach(transition -> adjacency.get(transition.getSource()).add(transition));
    }

    @Override
    protected Tuple2<LinkedHashSet<S>, ArrayList<ATransition<S, L>>> run() {
        LinkedHashSet<S> reachableStates = new LinkedHashSet<>();
        ArrayList<ATransition<S, L>> reachableTransitions = new ArrayList<>();
        fsm.getInitialStates().forEach(initialState -> run_(initialState, reachableStates, reachableTransitions));
        return new Tuple2<>(reachableStates, reachableTransitions);
    }

    private void run_(S startState, LinkedHashSet<S> reachableStates, ArrayList<ATransition<S, L>> reachableTransitions) {
        if (reachableStates.add(startState)) {
            adjacency.get(startState).forEach(transition -> {
                reachableTransitions.add(transition);
                run_(transition.getTarget(), reachableStates, reachableTransitions);
            });
        }
    }

}

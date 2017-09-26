package algs.heuristics;

import graphs.AbstractState;
import utilities.sets.Tuple2;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 28/06/17.
 * Time : 14:44
 */
public final class DefaultAbstractStatesOrderingFunction implements IAbstractStatesOrderingFunction {

    @Override
    public LinkedHashSet<AbstractState> apply(Tuple2<AbstractState, LinkedHashSet<AbstractState>> pair) {
        LinkedHashSet<AbstractState> orderedAbstractStates = new LinkedHashSet<>();
        orderedAbstractStates.add(pair.getFirst());
        orderedAbstractStates.addAll(pair.getSecond().stream().filter(abstractState -> !abstractState.equals(pair.getFirst())).collect(Collectors.toList()));
        return orderedAbstractStates;
    }

}

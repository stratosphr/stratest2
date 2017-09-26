package algs.heuristics;

import graphs.AbstractState;
import utilities.sets.Tuple2;

import java.util.LinkedHashSet;
import java.util.function.Function;

/**
 * Created by gvoiron on 28/06/17.
 * Time : 14:41
 */
public interface IAbstractStatesOrderingFunction extends Function<Tuple2<AbstractState, LinkedHashSet<AbstractState>>, LinkedHashSet<AbstractState>> {
}

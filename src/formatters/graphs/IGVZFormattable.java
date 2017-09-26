package formatters.graphs;

import graphs.AState;
import graphs.ATransition;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 12:42
 */
public interface IGVZFormattable<S extends AState, L> {

    LinkedHashSet<S> getInitialStates();

    LinkedHashSet<S> getStates();

    LinkedHashSet<? extends ATransition<S, L>> getTransitions();

    String accept(IGVZFormatter<S, L> formatter);

}

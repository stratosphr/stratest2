package formatters.graphs;

import graphs.AState;
import graphs.FSM;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 12:42
 */
public interface IGVZFormatter<S extends AState, L> {

    String visit(FSM<S, L> fsm);

}

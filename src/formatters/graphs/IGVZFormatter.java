package formatters.graphs;

import graphs.AFSM;
import graphs.AState;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 12:42
 */
public interface IGVZFormatter<S extends AState, L> {

    String visit(AFSM<S, L> fsm);

}

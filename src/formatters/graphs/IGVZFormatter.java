package formatters.graphs;

import graphs.AFSM;
import graphs.AState;
import utilities.ICloneable;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 12:42
 */
public interface IGVZFormatter<S extends AState, L extends ICloneable> {

    String visit(AFSM<S, L> fsm);

}

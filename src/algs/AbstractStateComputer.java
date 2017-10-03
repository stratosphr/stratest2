package algs;

import com.microsoft.z3.Status;
import graphs.AbstractState;
import graphs.ConcreteState;
import langs.eventb.Machine;
import langs.eventb.exprs.bool.And;
import solvers.z3.Z3;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 03/10/17.
 * Time : 10:58
 */
public class AbstractStateComputer extends AComputer<AbstractState> {

    private final ConcreteState c;
    private final LinkedHashSet<AbstractState> as;

    public AbstractStateComputer(ConcreteState c, LinkedHashSet<AbstractState> as) {
        this.c = c;
        this.as = as;
    }

    @Override
    protected AbstractState run() {
        for (AbstractState q : as) {
            if (Z3.checkSAT(new And(Machine.getInvariant(), q, c)) == Status.SATISFIABLE) {
                return q;
            }
        }
        throw new Error("State \"" + c + "\" does not satisfy any of the abstract states provided.");
    }

}

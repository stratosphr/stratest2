package algs;

import com.microsoft.z3.Status;
import graphs.AbstractState;
import langs.eventb.Machine;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.And;
import langs.eventb.exprs.bool.Predicate;
import solvers.z3.Z3;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeMap;

/**
 * Created by gvoiron on 28/06/17.
 * Time : 12:03
 */
public final class AbstractStatesComputer extends AComputer<LinkedHashSet<AbstractState>> {

    private final LinkedHashSet<Predicate> abstractionPredicates;

    public AbstractStatesComputer(LinkedHashSet<Predicate> abstractionPredicates) {
        this.abstractionPredicates = abstractionPredicates;
    }

    @Override
    protected LinkedHashSet<AbstractState> run() {
        LinkedHashSet<AbstractState> abstractStates = new LinkedHashSet<>();
        for (int i = 0; i < Math.pow(2, abstractionPredicates.size()); i++) {
            TreeMap<ABoolExpr, Boolean> mapping = new TreeMap<>();
            Iterator<Predicate> iterator = abstractionPredicates.iterator();
            String binaryString = String.format("%" + abstractionPredicates.size() + "s", Integer.toBinaryString(i)).replace(' ', '0');
            for (int j = 0; j < binaryString.length(); j++) {
                mapping.put(iterator.next(), binaryString.charAt(j) == '1');
            }
            AbstractState abstractState = new AbstractState("q" + i, mapping);
            //AbstractState abstractState = new AbstractState("q" + binaryString, mapping);
            if (Z3.checkSAT(new And(Machine.getInvariant(), abstractState)) == Status.SATISFIABLE) {
                abstractStates.add(abstractState);
            } else if (Z3.checkSAT(new And(Machine.getInvariant(), abstractState)) == Status.UNKNOWN) {
                throw new Error("Error: solver returned \"unknown\".");
            }
        }
        return abstractStates;
    }

}

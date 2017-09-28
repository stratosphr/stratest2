package algs.heuristics.relevance;

import com.microsoft.z3.Status;
import graphs.ConcreteState;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.AArithExpr;
import langs.eventb.exprs.arith.AValue;
import langs.eventb.exprs.bool.And;
import solvers.z3.Z3;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 23:10
 */
public class AtomicPredicateMultiImplies extends AAtomicPredicate {

    private final LinkedHashSet<AtomicPredicateImplies> implies;

    public AtomicPredicateMultiImplies(AtomicPredicateImplies... implies) {
        super(new And(implies));
        this.implies = new LinkedHashSet<>(Arrays.asList(implies));
    }

    @Override
    public AArithExpr getVariantC0(ConcreteState c, LinkedHashMap<ConcreteState, LinkedHashMap<AAtomicPredicate, AValue>> variantsMapping) {
        for (AtomicPredicateImplies ap : implies) {
            if (Z3.checkSAT(new And(
                    Machine.getInvariant(),
                    c,
                    ap.getCondition()
            )) == Status.SATISFIABLE) {
                return ap.getThenPart().getVariantC0(c, variantsMapping);
            }
        }
        throw new Error("Impossible case: at least one implication condition should be satisfiable with regards to \"" + c + "\".");
    }

    @Override
    public AArithExpr getVariantC_(ConcreteState c, ConcreteState c_, LinkedHashMap<ConcreteState, LinkedHashMap<AAtomicPredicate, AValue>> variantsMapping) {
        for (AtomicPredicateImplies ap : implies) {
            if (Z3.checkSAT(new And(
                    Machine.getInvariant(),
                    Machine.getInvariant().prime(),
                    c,
                    c_.prime(),
                    new And(ap.getCondition(), ap.getThenPart())
            )) == Status.SATISFIABLE) {
                return ap.getThenPart().getVariantC_(c, c_, variantsMapping);
            }
        }
        return variantsMapping.get(c).get(this);
    }

    public LinkedHashSet<AtomicPredicateImplies> getImplies() {
        return implies;
    }

    @Override
    public AtomicPredicateMultiImplies clone() {
        return new AtomicPredicateMultiImplies(implies.stream().map(AtomicPredicateImplies::clone).toArray(AtomicPredicateImplies[]::new));
    }

}

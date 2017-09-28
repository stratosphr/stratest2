package algs.heuristics.relevance;

import com.microsoft.z3.Status;
import graphs.ConcreteState;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.AArithExpr;
import langs.eventb.exprs.arith.AValue;
import langs.eventb.exprs.arith.Int;
import langs.eventb.exprs.arith.Minus;
import langs.eventb.exprs.bool.And;
import solvers.z3.Z3;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 23:10
 */
public class AtomicPredicateMultiImpliesV2 extends AAtomicPredicate {

    private final LinkedHashSet<AtomicPredicateImplies> implies;

    public AtomicPredicateMultiImpliesV2(AtomicPredicateImplies... implies) {
        super(new And(implies));
        this.implies = new LinkedHashSet<>(Arrays.asList(implies));
    }

    @Override
    public AArithExpr getVariantC0(ConcreteState c) {
        for (AtomicPredicateImplies ap : implies) {
            if (Z3.checkSAT(new And(
                    Machine.getInvariant(),
                    c,
                    ap.getCondition()
            )) == Status.SATISFIABLE) {
                return ap.getThenPart().getVariantC0(c);
            }
        }
        throw new Error("Impossible case: at least one implication condition should be satisfiable with regards to \"" + c + "\".");
    }

    @Override
    public AArithExpr getVariantC_(ConcreteState c, ConcreteState c_, LinkedHashMap<ConcreteState, LinkedHashMap<AAtomicPredicate, AValue>> variantsMapping) {
        return new Minus(variantsMapping.get(c).get(this), new Int(1));
    }

    public LinkedHashSet<AtomicPredicateImplies> getImplies() {
        return implies;
    }

    @Override
    public AtomicPredicateMultiImpliesV2 clone() {
        return new AtomicPredicateMultiImpliesV2(implies.stream().map(AtomicPredicateImplies::clone).toArray(AtomicPredicateImplies[]::new));
    }

}

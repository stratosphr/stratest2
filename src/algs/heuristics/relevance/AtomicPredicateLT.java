package algs.heuristics.relevance;

import graphs.ConcreteState;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.LT;

import java.util.LinkedHashMap;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 23:11
 */
public class AtomicPredicateLT extends AAtomicPredicate {

    private final AAssignable assignable;

    public AtomicPredicateLT(AAssignable assignable) {
        super(new LT(assignable.prime(), assignable));
        this.assignable = assignable;
    }

    @Override
    public AArithExpr getVariantC0(ConcreteState c, LinkedHashMap<ConcreteState, LinkedHashMap<AAtomicPredicate, AValue>> variantsMapping) {
        return new Minus(assignable, assignable instanceof Var ? Machine.getVarsDefs().get(assignable.getName()).getSet().iterator().next() : Machine.getFunsDefs().get(assignable.getName()).getSecond().getSet().iterator().next());
    }

    @Override
    public AArithExpr getVariantC_(ConcreteState c, ConcreteState c_, LinkedHashMap<ConcreteState, LinkedHashMap<AAtomicPredicate, AValue>> variantsMapping) {
        return new Minus(variantsMapping.get(c).get(this), new Int(1));
    }

    @Override
    public AAtomicPredicate clone() {
        return new AtomicPredicateLT(assignable.clone());
    }

}

package algs.heuristics.relevance;

import graphs.ConcreteState;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.LT;
import langs.eventb.exprs.sets.ASetExpr;

import java.util.Iterator;
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
    public AArithExpr getVariantC0(ConcreteState c) {
        //return new Minus(assignable, assignable instanceof Var ? Machine.getVarsDefs().get(assignable.getName()).getSet().iterator().next() : Machine.getFunsDefs().get(assignable.getName()).getSecond().getSet().iterator().next());
        ASetExpr set;
        if (assignable instanceof Var) {
            set = Machine.getVarsDefs().get(assignable.getName());
        } else {
            set = Machine.getFunsDefs().get(assignable.getName()).getSecond();
        }
        Iterator<AValue> iterator = set.getSet().iterator();
        AValue max;
        do {
            max = iterator.next();
        } while (iterator.hasNext());
        return max;
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

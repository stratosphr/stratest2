package algs.heuristics.relevance;

import graphs.ConcreteState;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.GT;
import langs.eventb.exprs.sets.ASetExpr;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 23:11
 */
public class AtomicPredicateGT extends AAtomicPredicate {

    private final AAssignable assignable;

    public AtomicPredicateGT(AAssignable assignable) {
        super(new GT(assignable.prime(), assignable));
        this.assignable = assignable;
    }

    @Override
    public AArithExpr getVariantC0(ConcreteState c, LinkedHashMap<ConcreteState, LinkedHashMap<AAtomicPredicate, AValue>> variantsMapping) {
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
        return new Minus(max, assignable);
    }

    @Override
    public AArithExpr getVariantC_(ConcreteState c, ConcreteState c_, LinkedHashMap<ConcreteState, LinkedHashMap<AAtomicPredicate, AValue>> variantsMapping) {
        if (variantsMapping.get(c).get(this) == null) {
            System.err.println(c);
            System.err.println(this);
        }
        return new Minus(variantsMapping.get(c).get(this), new Int(1));
    }

    @Override
    public AtomicPredicateGT clone() {
        return new AtomicPredicateGT(assignable.clone());
    }

}

package algs.heuristics.relevance;

import graphs.ConcreteState;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.And;
import langs.eventb.exprs.bool.Equals;

import java.util.LinkedHashMap;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 22:50
 */
public final class AtomicPredicateEnumSet extends AAtomicPredicate {

    private final AAssignable assignable;
    private final AArithExpr value;
    private final AArithExpr value_;

    public AtomicPredicateEnumSet(AAssignable assignable, AArithExpr value, AArithExpr value_) {
        super(new And(new Equals(assignable, value), new Equals(assignable.prime(), value_)));
        this.assignable = assignable;
        this.value = value;
        this.value_ = value_;
    }

    @Override
    public AArithExpr getVariantC0(ConcreteState c) {
        return new Int(assignable instanceof Var ? Machine.getVarsDefs().get(assignable.getName()).getSet().size() : Machine.getFunsDefs().get(assignable.getName()).getSecond().getSet().size());
    }

    @Override
    public AArithExpr getVariantC_(ConcreteState c, ConcreteState c_, LinkedHashMap<ConcreteState, LinkedHashMap<AAtomicPredicate, AValue>> variantsMapping) {
        return new Minus(variantsMapping.get(c).get(this), new Int(1));
    }

    @Override
    public AAtomicPredicate clone() {
        return new AtomicPredicateEnumSet(assignable.clone(), value.clone(), value_.clone());
    }

}

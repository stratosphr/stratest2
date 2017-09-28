package algs.heuristics.relevance;

import graphs.ConcreteState;
import langs.eventb.exprs.arith.AArithExpr;
import langs.eventb.exprs.arith.AValue;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.Implies;

import java.util.LinkedHashMap;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 23:35
 */
public final class AtomicPredicateImplies extends AAtomicPredicate {

    private final ABoolExpr condition;
    private final AAtomicPredicate thenPart;

    public AtomicPredicateImplies(ABoolExpr condition, AAtomicPredicate thenPart) {
        super(new Implies(condition, thenPart));
        this.condition = condition;
        this.thenPart = thenPart;
    }

    @Override
    public AArithExpr getVariantC0(ConcreteState c, LinkedHashMap<ConcreteState, LinkedHashMap<AAtomicPredicate, AValue>> variantsMapping) {
        System.err.println("HereC0");
        System.exit(42);
        return null;
    }

    @Override
    public AArithExpr getVariantC_(ConcreteState c, ConcreteState c_, LinkedHashMap<ConcreteState, LinkedHashMap<AAtomicPredicate, AValue>> variantsMapping) {
        System.err.println("HereC_");
        System.exit(42);
        return null;
    }

    @Override
    public AtomicPredicateImplies clone() {
        return new AtomicPredicateImplies(condition.clone(), thenPart.clone());
    }

    public ABoolExpr getCondition() {
        return condition;
    }

    public AAtomicPredicate getThenPart() {
        return thenPart;
    }

}

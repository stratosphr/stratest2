package algs.heuristics.relevance;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import graphs.ConcreteState;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.ABoolExpr;
import visitors.primer.IPrimerVisitor;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 22:25
 */
public abstract class AAtomicPredicate extends ABoolExpr {

    private ABoolExpr expr;

    public AAtomicPredicate(ABoolExpr expr) {
        this.expr = expr;
    }

    public abstract AArithExpr getVariantC0(ConcreteState c, LinkedHashMap<ConcreteState, LinkedHashMap<AAtomicPredicate, AValue>> variantsMapping);

    public abstract AArithExpr getVariantC_(ConcreteState c, ConcreteState c_, LinkedHashMap<ConcreteState, LinkedHashMap<AAtomicPredicate, AValue>> variantsMapping);

    @Override
    public abstract AAtomicPredicate clone();

    @Override
    public final String accept(ISMT2Visitor visitor) {
        return expr.accept(visitor);
    }

    @Override
    public final ABoolExpr accept(IPrimerVisitor visitor) {
        return expr.accept(visitor);
    }

    @Override
    public final String accept(IExprVisitor visitor) {
        return expr.accept(visitor);
    }

    @Override
    public final LinkedHashSet<Const> getConsts() {
        return expr.getConsts();
    }

    @Override
    public final LinkedHashSet<Var> getVars() {
        return expr.getVars();
    }

    @Override
    public final LinkedHashSet<Fun> getFuns() {
        return expr.getFuns();
    }

}

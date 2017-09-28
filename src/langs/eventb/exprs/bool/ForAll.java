package langs.eventb.exprs.bool;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.arith.Var;
import langs.eventb.exprs.sets.ASetExpr;
import utilities.sets.Tuple2;
import visitors.primer.IPrimerVisitor;

import java.util.function.IntFunction;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 10:15
 */
public final class ForAll extends AQuantifier {

    @SafeVarargs
    public ForAll(ABoolExpr expr, Tuple2<Var, ASetExpr>... quantifiedVarsDefs) {
        super(expr, quantifiedVarsDefs);
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(ISMT2Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public ForAll accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public ABoolExpr clone() {
        return new ForAll(expr.clone(), quantifiedVarsDefs.stream().map(tuple -> new Tuple2<>(tuple.getFirst().clone(), tuple.getSecond().clone())).toArray((IntFunction<Tuple2<Var, ASetExpr>[]>) Tuple2[]::new));
    }
}

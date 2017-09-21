package langs.eventb.exprs.bool;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.arith.Var;
import langs.eventb.exprs.sets.ASetExpr;
import utilities.Tuple;
import visitors.primer.IPrimerVisitor;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 10:15
 */
public final class Exists extends AQuantifier {

    @SafeVarargs
    public Exists(ABoolExpr expr, Tuple<Var, ASetExpr>... quantifiedVarsDefs) {
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
    public Exists accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

}

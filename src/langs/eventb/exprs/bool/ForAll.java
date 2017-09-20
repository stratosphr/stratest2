package langs.eventb.exprs.bool;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.arith.Var;
import visitors.primer.IPrimerVisitor;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 10:15
 */
public final class ForAll extends AQuantifier {

    public ForAll(ABoolExpr expr, Var... quantifiedVars) {
        super(expr, quantifiedVars);
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

}

package langs.exprs.bool;

import formatters.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.exprs.arith.Var;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 10:15
 */
public final class Exists extends AQuantifier {

    public Exists(ABoolExpr expr, Var... quantifiedVars) {
        super(expr, quantifiedVars);
    }

    @Override
    public void accept(ISMT2Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(IExprVisitor visitor) {
        visitor.visit(this);
    }

}

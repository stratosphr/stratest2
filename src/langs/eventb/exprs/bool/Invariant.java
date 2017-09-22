package langs.eventb.exprs.bool;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Fun;
import langs.eventb.exprs.arith.Var;
import visitors.primer.IPrimerVisitor;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 21/09/17.
 * Time : 11:43
 */
public final class Invariant extends ABoolExpr {

    private ABoolExpr expr;

    public Invariant(ABoolExpr expr) {
        this.expr = expr;
    }

    @Override
    public String accept(ISMT2Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public ABoolExpr accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return expr.getConsts();
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return expr.getVars();
    }

    @Override
    public LinkedHashSet<Fun> getFuns() {
        return expr.getFuns();
    }

    public ABoolExpr getExpr() {
        return expr;
    }

}

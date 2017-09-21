package langs.eventb.exprs.bool;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.arith.AArithExpr;
import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Var;
import langs.eventb.exprs.sets.ASetExpr;
import visitors.primer.IPrimerVisitor;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 21/09/17.
 * Time : 19:06
 */
public final class InDomain extends ABoolExpr {

    private final AArithExpr expr;
    private final ASetExpr domain;

    public InDomain(AArithExpr expr, ASetExpr domain) {
        this.expr = expr;
        this.domain = domain;
    }

    public AArithExpr getExpr() {
        return expr;
    }

    public ASetExpr getDomain() {
        return domain;
    }

    @Override
    public String accept(ISMT2Visitor visitor) {
        return null;
    }

    @Override
    public ABoolExpr accept(IPrimerVisitor visitor) {
        return null;
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return null;
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return null;
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return null;
    }
}

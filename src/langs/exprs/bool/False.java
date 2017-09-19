package langs.exprs.bool;

import formatters.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.exprs.arith.Const;
import langs.exprs.arith.Var;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:20
 */
public final class False extends ABoolExpr {

    @Override
    public void accept(IExprVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(ISMT2Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return new LinkedHashSet<>();
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return new LinkedHashSet<>();
    }

}

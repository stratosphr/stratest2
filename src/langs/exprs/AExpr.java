package langs.exprs;

import formatters.exprs.ExprFormatter;
import formatters.exprs.IExprVisitable;
import formatters.smt.ISMT2Visitable;
import langs.exprs.arith.Const;
import langs.exprs.arith.Var;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 11:41
 */
public abstract class AExpr implements IExprVisitable, ISMT2Visitable, Comparable<AExpr> {

    public abstract LinkedHashSet<Const> getConsts();

    public abstract LinkedHashSet<Var> getVars();

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return getClass().getName().equals(o.getClass().getName()) && toString().equals(o.toString());
    }

    @Override
    public int compareTo(AExpr aExpr) {
        return toString().compareTo(aExpr.toString());
    }

    @Override
    public String toString() {
        return ExprFormatter.format(this);
    }

}

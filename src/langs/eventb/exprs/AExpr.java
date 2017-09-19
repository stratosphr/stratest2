package langs.eventb.exprs;

import formatters.eventb.exprs.ExprFormatter;
import formatters.eventb.exprs.IExprVisitable;
import formatters.smt.ISMT2Visitable;
import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Var;

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

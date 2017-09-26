package langs.eventb.exprs;

import formatters.eventb.exprs.ExprFormatter;
import formatters.eventb.exprs.IExprVisitable;
import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Fun;
import langs.eventb.exprs.arith.Var;
import visitors.primer.IPrimerVisitable;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 11:41
 */
public abstract class AExpr<T extends AExpr> implements IExprVisitable, IPrimerVisitable<T>, Comparable<AExpr> {

    public abstract LinkedHashSet<Const> getConsts();

    public abstract LinkedHashSet<Var> getVars();

    public abstract LinkedHashSet<Fun> getFuns();

    public abstract T unprime();

    public abstract T prime();

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return getClass().getName().equals(o.getClass().getName()) && toString().equals(o.toString());
    }

    @Override
    public final String toString() {
        return ExprFormatter.format(this);
    }

    @Override
    public int compareTo(AExpr expr) {
        return toString().compareTo(expr.toString());
    }

}

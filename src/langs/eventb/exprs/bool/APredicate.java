package langs.eventb.exprs.bool;

import langs.eventb.exprs.AExpr;
import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Fun;
import langs.eventb.exprs.arith.Var;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 16:03
 */
public abstract class APredicate extends ABoolExpr {

    private String name;
    private ABoolExpr expr;

    public APredicate(String name, ABoolExpr expr) {
        this.name = name;
        this.expr = expr;
    }

    public final String getName() {
        return name;
    }

    public final ABoolExpr getExpr() {
        return expr;
    }

    @Override
    public final LinkedHashSet<Const> getConsts() {
        return getExpr().getConsts();
    }

    @Override
    public final LinkedHashSet<Var> getVars() {
        return getExpr().getVars();
    }

    @Override
    public final LinkedHashSet<Fun> getFuns() {
        return getExpr().getFuns();
    }

    @Override
    public final int hashCode() {
        return getExpr().hashCode();
    }

    @Override
    public final boolean equals(Object o) {
        return getClass().getName().equals(o.getClass().getName()) && getExpr().equals(((APredicate) o).getExpr());
    }

    @Override
    public final int compareTo(AExpr aExpr) {
        return aExpr instanceof APredicate ? getExpr().compareTo(((APredicate) aExpr).getExpr()) : getExpr().compareTo(aExpr);
    }

}

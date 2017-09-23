package langs.eventb.exprs.sets;

import formatters.eventb.exprs.IExprVisitor;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.AValue;
import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Fun;
import langs.eventb.exprs.arith.Var;
import visitors.primer.IPrimerVisitor;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 23/09/17.
 * Time : 01:26
 */
public final class NamedSet extends ASetExpr {

    private String name;

    public NamedSet(String name) {
        this.name = name;
    }

    @Override
    public NamedSet accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<AValue> getSet() {
        return Machine.getSingleton().getSetsDefs().get(name).getSet();
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return new LinkedHashSet<>();
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return new LinkedHashSet<>();
    }

    @Override
    public LinkedHashSet<Fun> getFuns() {
        return new LinkedHashSet<>();
    }

    public String getName() {
        return name;
    }

}

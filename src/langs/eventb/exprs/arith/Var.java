package langs.eventb.exprs.arith;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;

import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:48
 */
public final class Var extends AAssignable {

    public Var(String name) {
        super(name);
    }

    @Override
    public String accept(ISMT2Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return new LinkedHashSet<>();
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return new LinkedHashSet<>(Collections.singletonList(this));
    }

}

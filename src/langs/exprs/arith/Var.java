package langs.exprs.arith;

import visitors.exprs.IExprVisitor;
import visitors.smt.ISMT2Visitor;

import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:48
 */
public final class Var extends AArithExpr {

    private String name;

    public Var(String name) {
        this.name = name;
    }

    @Override
    public void accept(ISMT2Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(IExprVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return new LinkedHashSet<>();
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return new LinkedHashSet<>(Collections.singletonList(this));
    }

    public String getName() {
        return name;
    }

}

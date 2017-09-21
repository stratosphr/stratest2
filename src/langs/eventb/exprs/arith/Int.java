package langs.eventb.exprs.arith;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import visitors.primer.IPrimerVisitor;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:46
 */
public final class Int extends AValue {

    private Integer value;

    public Int(Integer value) {
        this.value = value;
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(ISMT2Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Int accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return new LinkedHashSet<>();
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return new LinkedHashSet<>();
    }

    public Integer getValue() {
        return value;
    }

}

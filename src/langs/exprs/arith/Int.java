package langs.exprs.arith;

import visitors.exprs.IExprVisitor;
import visitors.smt.ISMT2Visitor;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:46
 */
public final class Int extends AArithExpr {

    private Integer value;

    public Int(Integer value) {
        this.value = value;
    }

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

    public Integer getValue() {
        return value;
    }

}

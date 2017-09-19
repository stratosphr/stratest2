package langs.exprs.bool;

import formatters.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.exprs.arith.Const;
import langs.exprs.arith.Var;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 15/09/17.
 * Time : 15:30
 */
public final class Not extends ABoolExpr {

    private final ABoolExpr operand;

    public Not(ABoolExpr operand) {
        this.operand = operand;
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
        return operand.getConsts();
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return operand.getVars();
    }

    public ABoolExpr getOperand() {
        return operand;
    }

}

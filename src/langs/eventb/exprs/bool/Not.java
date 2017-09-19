package langs.eventb.exprs.bool;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Var;

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
    public String accept(ISMT2Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
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
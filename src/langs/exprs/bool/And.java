package langs.exprs.bool;

import langs.exprs.AExpr;
import langs.exprs.arith.Const;
import langs.exprs.arith.Var;
import visitors.exprs.IExprVisitor;
import visitors.smt.ISMT2Visitor;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:26
 */
public final class And extends ABoolExpr {

    private final LinkedHashSet<ABoolExpr> operands;

    public And(ABoolExpr... operands) {
        this.operands = new LinkedHashSet<>(Arrays.asList(operands));
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
        return operands.stream().map(AExpr::getConsts).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return operands.stream().map(AExpr::getVars).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public LinkedHashSet<ABoolExpr> getOperands() {
        return operands;
    }

}

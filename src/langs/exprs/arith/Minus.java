package langs.exprs.arith;

import langs.exprs.AExpr;
import visitors.exprs.IExprVisitor;
import visitors.smt.ISMT2Visitor;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 15/09/17.
 * Time : 15:05
 */
public final class Minus extends AArithExpr {

    private final LinkedHashSet<AArithExpr> operands;

    public Minus(AArithExpr... operands) {
        this.operands = new LinkedHashSet<>(Arrays.asList(operands));
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
        return operands.stream().map(AExpr::getConsts).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return operands.stream().map(AExpr::getVars).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public LinkedHashSet<AArithExpr> getOperands() {
        return operands;
    }

}

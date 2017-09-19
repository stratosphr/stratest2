package langs.exprs.bool;

import formatters.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.exprs.AExpr;
import langs.exprs.arith.AArithExpr;
import langs.exprs.arith.Const;
import langs.exprs.arith.Var;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:38
 */
public final class Equals extends ABoolExpr {

    private final LinkedHashSet<AArithExpr> operands;

    public Equals(AArithExpr... operands) {
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

    public LinkedHashSet<AArithExpr> getOperands() {
        return operands;
    }

}

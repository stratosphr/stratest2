package langs.eventb.exprs.arith;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.AExpr;
import visitors.primer.IPrimerVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 15/09/17.
 * Time : 15:05
 */
public final class Minus extends AArithExpr {

    private final ArrayList<AArithExpr> operands;

    public Minus(AArithExpr... operands) {
        this.operands = new ArrayList<>(Arrays.asList(operands));
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
    public Minus accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return operands.stream().map(AExpr::getConsts).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return operands.stream().map(AExpr::getVars).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Fun> getFuns() {
        return operands.stream().map(AExpr::getFuns).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public ArrayList<AArithExpr> getOperands() {
        return operands;
    }

    @Override
    public AArithExpr clone() {
        return new Minus(operands.stream().map(AArithExpr::clone).toArray(AArithExpr[]::new));
    }

}

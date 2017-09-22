package langs.eventb.exprs.arith;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.bool.ABoolExpr;
import visitors.primer.IPrimerVisitor;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:26
 */
public final class ArithITE extends AArithExpr {

    private final ABoolExpr condition;
    private final AArithExpr thenPart;
    private final AArithExpr elsePart;

    public ArithITE(ABoolExpr condition, AArithExpr thenPart, AArithExpr elsePart) {
        this.condition = condition;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
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
    public ArithITE accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return Stream.of(condition.getConsts(), thenPart.getConsts(), elsePart.getConsts()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return Stream.of(condition.getVars(), thenPart.getVars(), elsePart.getVars()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Fun> getFuns() {
        return Stream.of(condition.getFuns(), thenPart.getFuns(), elsePart.getFuns()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public ABoolExpr getCondition() {
        return condition;
    }

    public AArithExpr getThenPart() {
        return thenPart;
    }

    public AArithExpr getElsePart() {
        return elsePart;
    }

}

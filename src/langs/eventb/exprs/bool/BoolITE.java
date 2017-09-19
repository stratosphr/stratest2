package langs.eventb.exprs.bool;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Var;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:26
 */
public final class BoolITE extends ABoolExpr {

    private final ABoolExpr condition;
    private final ABoolExpr thenPart;
    private final ABoolExpr elsePart;

    public BoolITE(ABoolExpr condition, ABoolExpr thenPart, ABoolExpr elsePart) {
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
    public LinkedHashSet<Const> getConsts() {
        return Stream.of(condition.getConsts(), thenPart.getConsts(), elsePart.getConsts()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return Stream.of(condition.getVars(), thenPart.getVars(), elsePart.getVars()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public ABoolExpr getCondition() {
        return condition;
    }

    public ABoolExpr getThenPart() {
        return thenPart;
    }

    public ABoolExpr getElsePart() {
        return elsePart;
    }

}

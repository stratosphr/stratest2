package langs.exprs.bool;

import langs.exprs.arith.Const;
import langs.exprs.arith.Var;
import visitors.exprs.IExprVisitor;
import visitors.smt.ISMT2Visitor;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:26
 */
public final class Implies extends ABoolExpr {

    private final ABoolExpr condition;
    private final ABoolExpr thenPart;

    public Implies(ABoolExpr condition, ABoolExpr thenPart) {
        this.condition = condition;
        this.thenPart = thenPart;
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
        return Stream.of(condition.getConsts(), thenPart.getConsts()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return Stream.of(condition.getVars(), thenPart.getVars()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public ABoolExpr getCondition() {
        return condition;
    }

    public ABoolExpr getThenPart() {
        return thenPart;
    }

}

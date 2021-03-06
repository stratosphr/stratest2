package langs.eventb.exprs.bool;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Fun;
import langs.eventb.exprs.arith.Var;
import visitors.primer.IPrimerVisitor;

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
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(ISMT2Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Implies accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return Stream.of(condition.getConsts(), thenPart.getConsts()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return Stream.of(condition.getVars(), thenPart.getVars()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Fun> getFuns() {
        return Stream.of(condition.getFuns(), thenPart.getFuns()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public ABoolExpr clone() {
        return new Implies(condition.clone(), thenPart.clone());
    }

    public ABoolExpr getCondition() {
        return condition;
    }

    public ABoolExpr getThenPart() {
        return thenPart;
    }

}

package langs.eventb.exprs.bool;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.arith.AArithExpr;
import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Fun;
import langs.eventb.exprs.arith.Var;
import langs.eventb.exprs.sets.ASetExpr;
import visitors.primer.IPrimerVisitor;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gvoiron on 21/09/17.
 * Time : 19:06
 */
public final class InDomain extends ABoolExpr {

    private final AArithExpr expr;
    private final ASetExpr domain;

    public InDomain(AArithExpr expr, ASetExpr domain) {
        this.expr = expr;
        this.domain = domain;
    }

    @Override
    public String accept(ISMT2Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public InDomain accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return Stream.of(expr.getConsts(), domain.getConsts()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return Stream.of(expr.getVars(), domain.getVars()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Fun> getFuns() {
        return Stream.of(expr.getFuns(), domain.getFuns()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public AArithExpr getExpr() {
        return expr;
    }

    public ASetExpr getDomain() {
        return domain;
    }

}

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
 * Time : 13:09
 */
public final class Equiv extends ABoolExpr {

    private final ABoolExpr left;
    private final ABoolExpr right;

    public Equiv(ABoolExpr left, ABoolExpr right) {
        this.left = left;
        this.right = right;
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
    public Equiv accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return Stream.of(left.getConsts(), right.getConsts()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return Stream.of(left.getVars(), right.getVars()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Fun> getFuns() {
        return Stream.of(left.getFuns(), right.getFuns()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public ABoolExpr clone() {
        return new Equiv(left.clone(), right.clone());
    }

    public ABoolExpr getLeft() {
        return left;
    }

    public ABoolExpr getRight() {
        return right;
    }

}

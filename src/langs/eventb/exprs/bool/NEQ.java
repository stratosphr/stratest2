package langs.eventb.exprs.bool;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.AExpr;
import langs.eventb.exprs.arith.AArithExpr;
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
 * Time : 12:38
 */
public final class NEQ extends ABoolExpr {

    private final AArithExpr left;
    private final AArithExpr right;

    public NEQ(AArithExpr left, AArithExpr right) {
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
    public NEQ accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return Stream.of(left, right).map(AExpr::getConsts).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return Stream.of(left, right).map(AExpr::getVars).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Fun> getFuns() {
        return Stream.of(left, right).map(AExpr::getFuns).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public ABoolExpr clone() {
        return new NEQ(left.clone(), right.clone());
    }

    public AArithExpr getLeft() {
        return left;
    }

    public AArithExpr getRight() {
        return right;
    }

}

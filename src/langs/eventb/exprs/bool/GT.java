package langs.eventb.exprs.bool;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.arith.AArithExpr;
import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Var;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 13:09
 */
public final class GT extends ABoolExpr {

    private final AArithExpr left;
    private final AArithExpr right;

    public GT(AArithExpr left, AArithExpr right) {
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
    public LinkedHashSet<Const> getConsts() {
        return Stream.of(left.getConsts(), right.getConsts()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return Stream.of(left.getVars(), right.getVars()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public AArithExpr getLeft() {
        return left;
    }

    public AArithExpr getRight() {
        return right;
    }

}

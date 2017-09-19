package langs.exprs.bool;

import formatters.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.exprs.arith.Const;
import langs.exprs.arith.Var;

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
    public void accept(IExprVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(ISMT2Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return Stream.of(left.getConsts(), right.getConsts()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return Stream.of(left.getVars(), right.getVars()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public ABoolExpr getLeft() {
        return left;
    }

    public ABoolExpr getRight() {
        return right;
    }

}

package langs.eventb.substitutions;

import formatters.eventb.IEventBVisitor;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.And;
import langs.eventb.exprs.bool.Not;
import langs.eventb.exprs.bool.Or;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 20:54
 */
public final class IfThenElse extends ASubstitution {

    private final ABoolExpr condition;
    private final ASubstitution thenPart;
    private final ASubstitution elsePart;

    public IfThenElse(ABoolExpr condition, ASubstitution thenPart, ASubstitution elsePart) {
        this.condition = condition;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }

    @Override
    public String accept(IEventBVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public ABoolExpr getPrd(LinkedHashSet<AAssignable> assignables) {
        return new Or(new And(condition, thenPart.getPrd(assignables)), new And(new Not(condition), elsePart.getPrd(assignables)));
    }

    @Override
    public ASubstitution clone() {
        return new IfThenElse(condition.clone(), thenPart.clone(), elsePart.clone());
    }

    public ABoolExpr getCondition() {
        return condition;
    }

    public ASubstitution getThenPart() {
        return thenPart;
    }

    public ASubstitution getElsePart() {
        return elsePart;
    }

}

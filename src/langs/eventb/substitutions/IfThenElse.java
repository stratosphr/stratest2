package langs.eventb.substitutions;

import formatters.eventb.IEventBVisitor;
import langs.eventb.exprs.bool.ABoolExpr;

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

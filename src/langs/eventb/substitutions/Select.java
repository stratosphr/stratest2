package langs.eventb.substitutions;

import formatters.eventb.IEventBVisitor;
import langs.eventb.exprs.bool.ABoolExpr;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 20:54
 */
public final class Select extends ASubstitution {

    private final ABoolExpr condition;
    private final ASubstitution substitution;

    public Select(ABoolExpr condition, ASubstitution substitution) {
        this.condition = condition;
        this.substitution = substitution;
    }

    @Override
    public String accept(IEventBVisitor visitor) {
        return visitor.visit(this);
    }

    public ABoolExpr getCondition() {
        return condition;
    }

    public ASubstitution getSubstitution() {
        return substitution;
    }

}

package langs.eventb.substitutions;

import formatters.eventb.IEventBVisitor;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.And;

import java.util.LinkedHashSet;

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

    @Override
    public ABoolExpr getPrd(LinkedHashSet<AAssignable> assignables) {
        return new And(condition, substitution.getPrd(assignables));
    }

    @Override
    public ASubstitution clone() {
        return new Select(condition.clone(), substitution.clone());
    }

    public ABoolExpr getCondition() {
        return condition;
    }

    public ASubstitution getSubstitution() {
        return substitution;
    }

}

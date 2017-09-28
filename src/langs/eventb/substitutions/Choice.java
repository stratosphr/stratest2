package langs.eventb.substitutions;

import formatters.eventb.IEventBVisitor;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.Or;

import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 24/09/17.
 * Time : 18:41
 */
public final class Choice extends ASubstitution {

    private final LinkedHashSet<ASubstitution> substitutions;

    public Choice(ASubstitution... substitutions) {
        this.substitutions = new LinkedHashSet<>(Arrays.asList(substitutions));
    }

    @Override
    public String accept(IEventBVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public ABoolExpr getPrd(LinkedHashSet<AAssignable> assignables) {
        return new Or(substitutions.stream().map(substitution -> substitution.getPrd(assignables)).toArray(ABoolExpr[]::new));
    }

    @Override
    public ASubstitution clone() {
        return new Choice(substitutions.stream().map(ASubstitution::clone).toArray(ASubstitution[]::new));
    }

    public LinkedHashSet<ASubstitution> getSubstitutions() {
        return substitutions;
    }

}

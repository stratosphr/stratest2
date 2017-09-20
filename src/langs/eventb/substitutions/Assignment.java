package langs.eventb.substitutions;

import formatters.eventb.IEventBVisitor;
import langs.eventb.exprs.arith.AArithExpr;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.Equals;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 20:49
 */
public final class Assignment extends ASubstitution {

    private final AAssignable assignable;
    private final AArithExpr value;

    public Assignment(AAssignable assignable, AArithExpr value) {
        this.assignable = assignable;
        this.value = value;
    }

    @Override
    public String accept(IEventBVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public ABoolExpr getPrd(LinkedHashSet<AAssignable> assignables) {
        return new Equals(assignable.prime(), value);
    }

    public AAssignable getAssignable() {
        return assignable;
    }

    public AArithExpr getValue() {
        return value;
    }

}

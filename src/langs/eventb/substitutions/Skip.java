package langs.eventb.substitutions;

import formatters.eventb.IEventBVisitor;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.True;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 22:01
 */
public final class Skip extends ASubstitution {

    @Override
    public String accept(IEventBVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public ABoolExpr getPrd(LinkedHashSet<AAssignable> assignables) {
        return new True();
    }

}

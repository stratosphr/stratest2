package langs.eventb.substitutions;

import formatters.eventb.IEventBVisitor;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.arith.Var;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.And;
import langs.eventb.exprs.bool.Exists;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 20/09/17.
 * Time : 15:17
 */
public class Any extends ASubstitution {

    private final ABoolExpr condition;
    private final ASubstitution substitution;
    private final LinkedHashSet<Var> quantifiedVars;

    public Any(ABoolExpr condition, ASubstitution substitution, Var... quantifiedVars) {
        this.condition = condition;
        this.substitution = substitution;
        this.quantifiedVars = new LinkedHashSet<>(Arrays.asList(quantifiedVars));
    }

    @Override
    public String accept(IEventBVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public ABoolExpr getPrd(LinkedHashSet<AAssignable> assignables) {
        return new Exists(new And(condition, substitution.getPrd(assignables.stream().filter(assignable -> !(assignable instanceof Var) || !quantifiedVars.contains(assignable)).collect(Collectors.toCollection(LinkedHashSet::new)))), quantifiedVars.toArray(new Var[quantifiedVars.size()]));
    }

    public ABoolExpr getCondition() {
        return condition;
    }

    public ASubstitution getSubstitution() {
        return substitution;
    }

    public LinkedHashSet<Var> getQuantifiedVars() {
        return quantifiedVars;
    }

}

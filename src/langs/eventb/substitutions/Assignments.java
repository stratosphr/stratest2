package langs.eventb.substitutions;

import formatters.eventb.IEventBVisitor;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.And;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 20:49
 */
public final class Assignments extends ASubstitution {

    private final LinkedHashSet<Assignment> assignments;

    public Assignments(Assignment... assignments) {
        this.assignments = new LinkedHashSet<>(Arrays.asList(assignments));
    }

    @Override
    public String accept(IEventBVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public ABoolExpr getPrd(LinkedHashSet<AAssignable> assignables) {
        return new And(assignments.stream().map(assignment -> assignment.getPrd(new LinkedHashSet<>(Collections.singletonList(assignment.getAssignable())))).toArray(ABoolExpr[]::new));
    }

    public LinkedHashSet<Assignment> getAssignments() {
        return assignments;
    }

}

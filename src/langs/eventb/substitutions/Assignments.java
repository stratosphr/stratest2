package langs.eventb.substitutions;

import formatters.eventb.IEventBVisitor;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.arith.Fun;
import langs.eventb.exprs.arith.Var;
import langs.eventb.exprs.bool.*;
import utilities.sets.Tuple2;

import java.util.ArrayList;
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
        ArrayList<ABoolExpr> constraints = new ArrayList<>();
        assignables.stream().filter(assignable -> assignable instanceof Var && assignments.stream().noneMatch(assignment -> assignment.getAssignable().equals(assignable))).forEach(assignable -> constraints.add(new Equals(assignable.prime(), assignable)));
        assignments.forEach(assignment -> constraints.add(assignment.getPrd(new LinkedHashSet<>(Collections.singletonList(assignment.getAssignable())))));
        ArrayList<String> alreadyTreatedFuns = new ArrayList<>();
        assignables.stream().filter(assignable -> assignable instanceof Fun && !alreadyTreatedFuns.contains(assignable.getName())).forEach(fun -> {
            alreadyTreatedFuns.add(fun.getName());
            Var i = new Var("_i");
            Fun funI = new Fun(fun.getName(), i);
            constraints.add(
                    new ForAll(
                            new Implies(
                                    new And(assignments.stream().filter(assignment -> assignment.getAssignable() instanceof Fun && assignment.getAssignable().getName().equals(fun.getName())).map(assignment -> new NEQ(i, ((Fun) assignment.getAssignable()).getOperand())).toArray(ABoolExpr[]::new)),
                                    new Equals(funI.prime(), funI)
                            ),
                            new Tuple2<>(i, Machine.getFunsDefs().get(fun.getName()).getFirst())
                    )
            );
        });
        return new And(constraints.toArray(new ABoolExpr[constraints.size()]));
    }

    public LinkedHashSet<Assignment> getAssignments() {
        return assignments;
    }

}

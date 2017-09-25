package langs.eventb.substitutions;

import formatters.eventb.IEventBVisitor;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.AArithExpr;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.arith.Fun;
import langs.eventb.exprs.arith.Var;
import langs.eventb.exprs.bool.*;
import utilities.Tuple;

import java.util.ArrayList;
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
        if (assignables.size() == 1) {
            return new Equals(assignable.prime(), value);
        } else {
            ArrayList<ABoolExpr> constraints = new ArrayList<>();
            ArrayList<String> alreadyTreatedFuns = new ArrayList<>();
            for (AAssignable assignable : assignables) {
                if (assignable instanceof Var) {
                    constraints.add(new Equals(assignable.prime(), assignable));
                } else if (assignable instanceof Fun) {
                    if (assignable.getName().equals(this.assignable.getName()) && !alreadyTreatedFuns.contains(assignable.getName())) {
                        alreadyTreatedFuns.add(assignable.getName());
                        Var i = new Var("_i");
                        Fun fun = new Fun(this.assignable.getName(), i);
                        constraints.add(new Equals(this.assignable.prime(), value));
                        constraints.add(new ForAll(
                                new Implies(
                                        new NEQ(i, ((Fun) this.assignable).getOperand()),
                                        new Equals(fun.prime(), fun)
                                ),
                                new Tuple<>(i, Machine.getFunsDefs().get(assignable.getName()).getFirst())
                        ));
                    }
                }
            }
            return new And(constraints.toArray(new ABoolExpr[constraints.size()]));
        }
    }

    public AAssignable getAssignable() {
        return assignable;
    }

    public AArithExpr getValue() {
        return value;
    }

}

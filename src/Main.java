import com.microsoft.z3.Status;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.arith.Int;
import langs.eventb.exprs.arith.Plus;
import langs.eventb.exprs.arith.Var;
import langs.eventb.exprs.bool.Equals;
import langs.eventb.substitutions.Assignment;
import langs.eventb.substitutions.Assignments;
import langs.eventb.substitutions.Select;
import solvers.z3.Z3;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Assignments assignments = new Assignments(
                new Assignment(
                        new Var("var1"),
                        new Plus(
                                new Int(2),
                                new Var("var2"),
                                new Var("var3")
                        )
                )
        );
        Select substitution = new Select(
                new Equals(new Var("var1"), new Int(2)),
                new Assignments(
                        new Assignment(
                                new Var("var1"),
                                new Int(42)
                        )
                )
        );
        LinkedHashSet<AAssignable> vars = new LinkedHashSet<>(Arrays.asList(new Var("var1"), new Var("var2"), new Var("var3")));
        Status status = Z3.checkSAT(substitution.getPrd(vars));
        if (status == Status.SATISFIABLE) {
            System.out.println(Z3.getModel(vars));
            System.out.println(Z3.getModel(vars.stream().map(assignable -> (AAssignable) assignable.prime()).collect(Collectors.toCollection(LinkedHashSet::new))));
        }
    }

}

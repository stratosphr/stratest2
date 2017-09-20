import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.arith.Int;
import langs.eventb.exprs.arith.Plus;
import langs.eventb.exprs.arith.Var;
import langs.eventb.exprs.bool.Equals;
import langs.eventb.exprs.bool.False;
import langs.eventb.exprs.bool.True;
import langs.eventb.substitutions.*;

import java.util.Arrays;
import java.util.LinkedHashSet;

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
                new IfThenElse(
                        new False(),
                        new Select(
                                new Equals(new Var("var1"), new Int(2)),
                                new Any(
                                        new True(),
                                        new Select(
                                                new Equals(new Var("var1"), new Int(2)),
                                                new Any(
                                                        new True(),
                                                        new Skip(),
                                                        new Var("var1"),
                                                        new Var("var2")
                                                )
                                        ),
                                        new Var("var1"),
                                        new Var("var2")
                                )
                        ),
                        assignments
                )
        );
        LinkedHashSet<AAssignable> vars = new LinkedHashSet<>(Arrays.asList(new Var("var1"), new Var("var2"), new Var("var3")));
        System.out.println(substitution);
        System.out.println(substitution.getPrd(vars));
        System.out.println(substitution.getPrd(vars).prime());
        System.out.println(substitution.getPrd(vars).prime().prime());
        System.out.println(substitution.getPrd(vars).prime().prime().unprime());
    }

}

import formatters.smt.SMT2Formatter;
import langs.eventb.exprs.arith.Int;
import langs.eventb.exprs.arith.Plus;
import langs.eventb.exprs.arith.Var;
import langs.eventb.exprs.bool.Equals;
import langs.eventb.exprs.bool.Exists;
import langs.eventb.exprs.bool.False;
import langs.eventb.exprs.bool.Or;
import langs.eventb.substitutions.Assignment;
import langs.eventb.substitutions.IfThenElse;
import langs.eventb.substitutions.Select;
import langs.eventb.substitutions.Skip;

public class Main {

    public static void main(String[] args) {
        Select substitution = new Select(
                new Equals(new Var("var1"), new Int(2)),
                new IfThenElse(
                        new False(),
                        new Select(
                                new Equals(new Var("var1"), new Int(2)),
                                new Skip()
                        ),
                        new Assignment(
                                new Var("var1"),
                                new Plus(
                                        new Int(2),
                                        new Var("var2"),
                                        new Var("var3")
                                )
                        )
                )
        );
        System.out.println(SMT2Formatter.format(new Or(
                new Exists(new Equals(new Var("var1"), new Var("var2")), new Var("var1"))
        )));
    }

}

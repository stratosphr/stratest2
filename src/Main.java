import com.microsoft.z3.Status;
import langs.exprs.arith.*;
import langs.exprs.bool.*;
import solvers.z3.Z3;

public class Main {

    public static void main(String[] args) {
        Status status = Z3.checkSAT(new Or(
                new False(),
                new BoolITE(
                        new True(),
                        new Not(new False()),
                        new False()
                ),
                new Equals(
                        new Int(2),
                        new Const("test"),
                        new Plus(
                                new Minus(new Int(3), new Var("toto"), new Int(4))
                        ),
                        new Int(3),
                        new Var("lol")
                ),
                new GT(
                        new Const("test"),
                        new Var("lol")
                ),
                new LT(
                        new Const("test"),
                        new Var("lol")
                ),
                new LEQ(
                        new Const("test"),
                        new Var("lol")
                ),
                new GEQ(
                        new Const("test"),
                        new Var("lol")
                ),
                new False()
        ));
        System.out.println(status);
    }

}

import com.microsoft.z3.Status;
import langs.exprs.arith.Int;
import langs.exprs.arith.Var;
import langs.exprs.bool.Equals;
import langs.exprs.bool.False;
import langs.exprs.bool.Or;
import solvers.z3.Z3;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class Main {

    public static void main(String[] args) {
        Status status = Z3.checkSAT(new Or(
                new Equals(
                        new Var("toto"),
                        new Var("lol"),
                        new Int(4)
                ),
                new False()
        ));
        System.out.println(status);
        System.out.println(Z3.getModel(new LinkedHashSet<>(Arrays.asList(
                new Var("toto"),
                new Var("lol")
        ))));
    }

}

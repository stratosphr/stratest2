package solvers.z3;

import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import langs.exprs.bool.ABoolExpr;
import visitors.smt.SMT2Formatter;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 11:29
 */
public final class Z3 {

    private static Context context = new Context();
    private static Solver solver = context.mkSolver();

    private Z3() {
    }

    public static Status checkSAT(ABoolExpr boolExpr) {
        System.out.println(SMT2Formatter.format(boolExpr));
        solver.add(context.parseSMTLIB2String(SMT2Formatter.format(boolExpr), null, null, null, null));
        return solver.check();
    }

}

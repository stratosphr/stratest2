package solvers.z3;

import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import formatters.smt.SMT2Formatter;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.bool.ABoolExpr;

import java.util.Set;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 11:29
 */
public final class Z3 {

    private static boolean modelAvailable = false;
    private final static Context context = new Context();
    private final static Solver solver = context.mkSolver();

    private Z3() {
    }

    public static Status checkSAT(ABoolExpr boolExpr) {
        reset();
        solver.add(context.parseSMTLIB2String(SMT2Formatter.format(boolExpr), null, null, null, null));
        Status satisfiability = solver.check();
        modelAvailable = satisfiability == Status.SATISFIABLE;
        return satisfiability;
    }

    public static Model getModel(Set<AAssignable> assignables) {
        if (!modelAvailable) {
            throw new Error("A model cannot be computed before checking the satisfiability of a formula or if this formula is not SATISFIABLE.");
        } else {
            return new Model(context, solver.getModel(), assignables);
        }
    }

    private static void reset() {
        modelAvailable = false;
        solver.reset();
    }

}

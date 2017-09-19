package solvers.z3;

import com.microsoft.z3.Context;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.arith.Int;

import java.util.Set;
import java.util.TreeMap;

/**
 * Created by gvoiron on 15/09/17.
 * Time : 15:46
 */
final class Model extends TreeMap<AAssignable, Int> {

    Model(Context context, com.microsoft.z3.Model model, Set<AAssignable> assignables) {
        assignables.forEach(assignable -> put(assignable, new Int(Integer.parseInt(model.eval(context.mkIntConst(assignable.getName()), true).toString()))));
    }

}

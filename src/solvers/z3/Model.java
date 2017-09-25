package solvers.z3;

import com.microsoft.z3.Context;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.*;

import java.util.Set;
import java.util.TreeMap;

/**
 * Created by gvoiron on 15/09/17.
 * Time : 15:46
 */
public final class Model extends TreeMap<AAssignable, AValue> {

    Model(Context context, com.microsoft.z3.Model model, Set<AAssignable> assignables) {
        for (AAssignable assignable : assignables) {
            //put(assignable, new Int(Integer.parseInt(model.eval(context.mkIntConst(assignable instanceof Var ? assignable.getName() : assignable.getName() + Fun.getParameterDelimiter() + ((Fun) assignable).getOperand()), true).toString())));
            Int value = new Int(Integer.parseInt(model.eval(context.mkIntConst(assignable instanceof Var ? assignable.getName() : assignable.getName() + Fun.getParameterDelimiter() + ((Fun) assignable).getOperand()), true).toString()));
            if (assignable instanceof Var) {
                put(assignable, Machine.getVarsDefs().containsKey(assignable.getName()) ? Machine.getVarsDefs().get(assignable.getName()).retrieveValue(value) : value);
            } else {
                put(assignable, Machine.getFunsDefs().containsKey(assignable.getName()) ? Machine.getFunsDefs().get(assignable.getName()).getSecond().retrieveValue(value) : value);
            }
        }
    }

}

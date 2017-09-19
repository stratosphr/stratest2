package langs.eventb.exprs.bool;

import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Var;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 10:15
 */
public abstract class AQuantifier extends ABoolExpr {

    private final ABoolExpr expr;
    private final LinkedHashSet<Var> quantifiedVars;

    AQuantifier(ABoolExpr expr, Var... quantifiedVars) {
        this.expr = expr;
        this.quantifiedVars = new LinkedHashSet<>(Arrays.asList(quantifiedVars));
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return expr.getConsts();
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return expr.getVars().stream().filter(var -> !quantifiedVars.contains(var)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public ABoolExpr getExpr() {
        return expr;
    }

    public LinkedHashSet<Var> getQuantifiedVars() {
        return quantifiedVars;
    }

}

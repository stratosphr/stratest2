package langs.eventb.exprs.bool;

import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Fun;
import langs.eventb.exprs.arith.Var;
import langs.eventb.exprs.sets.ASetExpr;
import utilities.sets.Tuple2;

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
    private final LinkedHashSet<Tuple2<Var, ASetExpr>> quantifiedVarsDefs;

    @SafeVarargs
    AQuantifier(ABoolExpr expr, Tuple2<Var, ASetExpr>... quantifiedVarsDefs) {
        this.expr = expr;
        this.quantifiedVars = new LinkedHashSet<>(Arrays.stream(quantifiedVarsDefs).map(Tuple2::getFirst).collect(Collectors.toList()));
        this.quantifiedVarsDefs = new LinkedHashSet<>(Arrays.asList(quantifiedVarsDefs));
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return expr.getConsts();
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return expr.getVars().stream().filter(var -> !quantifiedVars.contains(var)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Fun> getFuns() {
        return expr.getFuns();
    }

    public ABoolExpr getExpr() {
        return expr;
    }

    public LinkedHashSet<Var> getQuantifiedVars() {
        return quantifiedVars;
    }

    public LinkedHashSet<Tuple2<Var, ASetExpr>> getQuantifiedVarsDefs() {
        return quantifiedVarsDefs;
    }

}

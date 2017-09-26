package langs.eventb.substitutions;

import formatters.eventb.IEventBVisitor;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.arith.Var;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.And;
import langs.eventb.exprs.bool.Exists;
import langs.eventb.exprs.sets.ASetExpr;
import utilities.sets.Tuple2;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 20/09/17.
 * Time : 15:17
 */
public class Any extends ASubstitution {

    private final ABoolExpr condition;
    private final ASubstitution substitution;
    private final LinkedHashMap<Var, ASetExpr> quantifiedVarsDefs;

    @SafeVarargs
    public Any(ABoolExpr condition, ASubstitution substitution, Tuple2<Var, ASetExpr>... quantifiedVarsDefs) {
        this.condition = condition;
        this.substitution = substitution;
        this.quantifiedVarsDefs = new LinkedHashMap<>();
        Arrays.asList(quantifiedVarsDefs).forEach(tuple -> this.quantifiedVarsDefs.put(tuple.getFirst(), tuple.getSecond()));
    }

    @Override
    public String accept(IEventBVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public ABoolExpr getPrd(LinkedHashSet<AAssignable> assignables) {
        return new Exists(new And(condition, substitution.getPrd(assignables.stream().filter(assignable -> !(assignable instanceof Var) || !quantifiedVarsDefs.keySet().contains(assignable)).collect(Collectors.toCollection(LinkedHashSet::new)))), quantifiedVarsDefs.keySet().stream().map(var -> new Tuple2<>(var, quantifiedVarsDefs.get(var))).toArray((IntFunction<Tuple2<Var, ASetExpr>[]>) Tuple2[]::new));
    }

    public ABoolExpr getCondition() {
        return condition;
    }

    public ASubstitution getSubstitution() {
        return substitution;
    }

    public LinkedHashMap<Var, ASetExpr> getQuantifiedVarsDefs() {
        return quantifiedVarsDefs;
    }

}

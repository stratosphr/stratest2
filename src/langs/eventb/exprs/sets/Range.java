package langs.eventb.exprs.sets;

import formatters.eventb.exprs.IExprVisitor;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.And;
import langs.eventb.exprs.bool.Equals;
import solvers.z3.Model;
import solvers.z3.Z3;
import utilities.maths.Maths;
import visitors.primer.IPrimerVisitor;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gvoiron on 21/09/17.
 * Time : 10:54
 */
public final class Range extends ASetExpr {

    private final AArithExpr lowerBound;
    private final AArithExpr upperBound;
    private LinkedHashSet<AValue> set;

    public Range(AArithExpr lowerBound, AArithExpr upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public ASetExpr accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return Stream.of(lowerBound.getConsts(), upperBound.getConsts()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return Stream.of(lowerBound.getVars(), upperBound.getVars()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Fun> getFuns() {
        return Stream.of(lowerBound.getFuns(), upperBound.getFuns()).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<AValue> getSet() {
        if (set == null) {
            Var varLowerBound = new Var("_lowerBound");
            Var varUpperBound = new Var("_upperBound");
            Z3.checkSAT(new And(new Equals(varLowerBound, lowerBound), new Equals(varUpperBound, upperBound), new And(Machine.getConstsDefs().keySet().stream().map(constName -> new Equals(Machine.getConsts().get(constName), Machine.getConstsDefs().get(constName))).toArray(ABoolExpr[]::new))));
            Model model = Z3.getModel(new LinkedHashSet<>(Arrays.asList(varLowerBound, varUpperBound)));
            set = (model.get(varLowerBound).getValue() < model.get(varUpperBound).getValue() ? Maths.range(model.get(varLowerBound).getValue(), model.get(varUpperBound).getValue()) : Maths.range(model.get(varUpperBound).getValue(), model.get(varLowerBound).getValue())).stream().map(Int::new).collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return set;
    }

    public AArithExpr getLowerBound() {
        return lowerBound;
    }

    public AArithExpr getUpperBound() {
        return upperBound;
    }

}

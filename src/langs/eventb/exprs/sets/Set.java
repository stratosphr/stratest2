package langs.eventb.exprs.sets;

import formatters.eventb.exprs.IExprVisitor;
import langs.eventb.Machine;
import langs.eventb.exprs.AExpr;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.And;
import langs.eventb.exprs.bool.Equals;
import solvers.z3.Model;
import solvers.z3.Z3;
import visitors.primer.IPrimerVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 23/09/17.
 * Time : 02:04
 */
public final class Set extends ASetExpr {

    private ArrayList<AArithExpr> elements;
    private LinkedHashSet<AValue> set;

    public Set(AArithExpr... elements) {
        this.elements = new ArrayList<>(Arrays.stream(elements).collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    @Override
    public Set accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return elements.stream().map(AExpr::getConsts).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return elements.stream().map(AExpr::getVars).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<Fun> getFuns() {
        return elements.stream().map(AExpr::getFuns).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public LinkedHashSet<AValue> getSet() {
        if (set == null) {
            LinkedHashSet<AAssignable> vars = new LinkedHashSet<>();
            LinkedHashSet<Equals> equals = new LinkedHashSet<>();
            for (int i = 0; i < elements.size(); i++) {
                vars.add(new Var("_" + i));
                equals.add(new Equals(new Var("_" + i), elements.get(i)));
            }
            Z3.checkSAT(new And(new And(equals.toArray(new Equals[equals.size()])), new And(Machine.getConstsDefs().keySet().stream().map(constName -> new Equals(Machine.getConsts().get(constName), Machine.getConstsDefs().get(constName))).toArray(ABoolExpr[]::new))));
            Model model = Z3.getModel(vars);
            set = new LinkedHashSet<>(model.values());
        }
        return set;
    }

    @Override
    public AValue retrieveValue(Int value) {
        return value;
    }

    public ArrayList<AArithExpr> getElements() {
        return elements;
    }

}

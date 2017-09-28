package algs.heuristics.relevance;

import com.microsoft.z3.Status;
import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import graphs.ConcreteState;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.And;
import langs.eventb.exprs.bool.Not;
import langs.eventb.exprs.bool.Or;
import solvers.z3.Z3;
import visitors.primer.IPrimerVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 22:19
 */
public final class RelevancePredicate extends ABoolExpr {

    private final LinkedHashSet<AAtomicPredicate> atomicPredicates;

    public RelevancePredicate(AAtomicPredicate... atomicPredicates) {
        this.atomicPredicates = new LinkedHashSet<>(Arrays.asList(atomicPredicates));
    }

    @Override
    public String accept(ISMT2Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public RelevancePredicate accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

    public ABoolExpr getExpr() {
        return new Or(atomicPredicates.toArray(new ABoolExpr[atomicPredicates.size()]));
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return getExpr().getConsts();
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return getExpr().getVars();
    }

    @Override
    public LinkedHashSet<Fun> getFuns() {
        return getExpr().getFuns();
    }

    @Override
    public RelevancePredicate clone() {
        return new RelevancePredicate(atomicPredicates.stream().map(AAtomicPredicate::clone).toArray(AAtomicPredicate[]::new));
    }

    public LinkedHashSet<AAtomicPredicate> getAtomicPredicates() {
        return atomicPredicates;
    }

    public Plus getVariantC0(ConcreteState c) {
        return new Plus(atomicPredicates.stream().map(aAtomicPredicate -> aAtomicPredicate.getVariantC0(c)).toArray(AArithExpr[]::new));
    }

    public Plus getVariantC_(ConcreteState c, ConcreteState c_, LinkedHashMap<ConcreteState, LinkedHashMap<AAtomicPredicate, AValue>> variantsMapping) {
        ArrayList<AArithExpr> sumOperands = new ArrayList<>();
        atomicPredicates.forEach(ap -> {
            if (Z3.checkSAT(new And(
                    Machine.getInvariant(),
                    Machine.getInvariant().prime(),
                    c,
                    c_.prime(),
                    new Not(ap)
            )) == Status.SATISFIABLE) {
                sumOperands.add(variantsMapping.get(c).get(ap));
            } else if (Z3.checkSAT(new And(
                    Machine.getInvariant(),
                    Machine.getInvariant().prime(),
                    c,
                    c_.prime(),
                    ap
            )) == Status.SATISFIABLE) {
                sumOperands.add(ap.getVariantC_(c, c_, variantsMapping));
            } else {
                throw new Error("Impossible case in VariantC_ computation.");
            }
        });
        return new Plus(sumOperands.toArray(new AArithExpr[sumOperands.size()]));
    }

}

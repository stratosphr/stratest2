package visitors.primer;

import langs.eventb.exprs.AExpr;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.*;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 20/09/17.
 * Time : 15:00
 */
public final class Primer implements IPrimerVisitor {

    private final boolean isPriming;
    private LinkedHashSet<Var> quantifiedVars;

    private Primer(boolean isPriming) {
        this.quantifiedVars = new LinkedHashSet<>();
        this.isPriming = isPriming;
    }

    public static <T extends AExpr> T prime(AExpr<T> expr) {
        return expr.accept(new Primer(true));
    }

    public static <T extends AExpr> T unprime(AExpr<T> expr) {
        return expr.accept(new Primer(false));
    }

    public static String getPrimeSuffix() {
        return "_";
    }

    @Override
    public Int visit(Int anInt) {
        return new Int(anInt.getValue());
    }

    @Override
    public Const visit(Const aConst) {
        return new Const(aConst.getName());
    }

    @Override
    public Var visit(Var var) {
        return isPriming ? new Var(var.getName() + Primer.getPrimeSuffix()) : new Var(var.getName().replaceAll("_$", ""));
    }

    @Override
    public Plus visit(Plus plus) {
        return new Plus(plus.getOperands().stream().map(expr -> expr.accept(this)).toArray(AArithExpr[]::new));
    }

    @Override
    public Minus visit(Minus minus) {
        return new Minus(minus.getOperands().stream().map(expr -> expr.accept(this)).toArray(AArithExpr[]::new));
    }

    @Override
    public Times visit(Times times) {
        return new Times(times.getOperands().stream().map(expr -> expr.accept(this)).toArray(AArithExpr[]::new));
    }

    @Override
    public Div visit(Div div) {
        return new Div(div.getOperands().stream().map(expr -> expr.accept(this)).toArray(AArithExpr[]::new));
    }

    @Override
    public False visit(False aFalse) {
        return new False();
    }

    @Override
    public True visit(True aTrue) {
        return new True();
    }

    @Override
    public Not visit(Not not) {
        return new Not(not.getOperand().accept(this));
    }

    @Override
    public Or visit(Or or) {
        return new Or(or.getOperands().stream().map(aBoolExpr -> aBoolExpr.accept(this)).toArray(ABoolExpr[]::new));
    }

    @Override
    public And visit(And and) {
        return new And(and.getOperands().stream().map(aBoolExpr -> aBoolExpr.accept(this)).toArray(ABoolExpr[]::new));
    }

    @Override
    public Equals visit(Equals equals) {
        return new Equals(equals.getOperands().stream().map(expr -> expr.accept(this)).toArray(AArithExpr[]::new));
    }

    @Override
    public LT visit(LT lt) {
        return new LT(lt.getLeft().accept(this), lt.getRight().accept(this));
    }

    @Override
    public LEQ visit(LEQ leq) {
        return new LEQ(leq.getLeft().accept(this), leq.getRight().accept(this));
    }

    @Override
    public GT visit(GT gt) {
        return new GT(gt.getLeft().accept(this), gt.getRight().accept(this));
    }

    @Override
    public GEQ visit(GEQ geq) {
        return new GEQ(geq.getLeft().accept(this), geq.getRight().accept(this));
    }

    @Override
    public Implies visit(Implies implies) {
        return new Implies(implies.getCondition().accept(this), implies.getThenPart().accept(this));
    }

    @Override
    public Equiv visit(Equiv equiv) {
        return new Equiv(equiv.getLeft().accept(this), equiv.getRight().accept(this));
    }

    @Override
    public BoolITE visit(BoolITE boolITE) {
        return new BoolITE(boolITE.getCondition().accept(this), boolITE.getThenPart().accept(this), boolITE.getElsePart().accept(this));
    }

    @Override
    public Exists visit(Exists exists) {
        LinkedHashSet<Var> oldQuantifiedVariables = quantifiedVars;
        quantifiedVars = new LinkedHashSet<>();
        quantifiedVars.addAll(oldQuantifiedVariables);
        quantifiedVars.addAll(exists.getQuantifiedVars());
        Exists primed = new Exists(exists.getExpr().accept(this), exists.getQuantifiedVars().stream().map(quantifiedVariable -> quantifiedVariable.accept(this)).toArray(Var[]::new));
        quantifiedVars = oldQuantifiedVariables;
        return primed;
    }

    @Override
    public ForAll visit(ForAll forAll) {
        LinkedHashSet<Var> oldQuantifiedVariables = quantifiedVars;
        quantifiedVars = new LinkedHashSet<>();
        quantifiedVars.addAll(oldQuantifiedVariables);
        quantifiedVars.addAll(forAll.getQuantifiedVars());
        ForAll primed = new ForAll(forAll.getExpr().accept(this), forAll.getQuantifiedVars().stream().map(quantifiedVariable -> quantifiedVariable.accept(this)).toArray(Var[]::new));
        quantifiedVars = oldQuantifiedVariables;
        return primed;
    }

}

package visitors.smt;

import langs.exprs.arith.*;
import langs.exprs.bool.*;
import visitors.AFormatter;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 11:35
 */
public final class SMT2Formatter extends AFormatter implements ISMT2Visitor {

    private SMT2Formatter() {
    }

    public static String format(ABoolExpr boolExpr) {
        SMT2Formatter formatter = new SMT2Formatter();
        LinkedHashSet<Const> consts = boolExpr.getConsts();
        LinkedHashSet<Var> vars = boolExpr.getVars();
        if (!consts.isEmpty()) {
            formatter.formatLine("; CONSTS");
            consts.forEach(aConst -> formatter.formatLine("(declare-fun " + aConst.getName() + " () Int)"));
            formatter.formatLine("");
        }
        if (!vars.isEmpty()) {
            formatter.formatLine("; VARS");
            vars.forEach(var -> formatter.formatLine("(declare-fun " + var.getName() + " () Int)"));
            formatter.formatLine("");
        }
        formatter.formatLine("(assert");
        formatter.indentRight();
        boolExpr.accept(formatter);
        formatter.indentLeft();
        formatter.formatLine(")");
        return formatter.getFormatted().toString();
    }

    @Override
    public void visit(Int anInt) {
        formatLine(anInt.getValue().toString());
    }

    @Override
    public void visit(Const aConst) {
        formatLine(aConst.getName());
    }

    @Override
    public void visit(Var var) {
        formatLine(var.getName());
    }

    @Override
    public void visit(Plus plus) {
        formatLine("(+");
        indentRight();
        plus.getOperands().forEach(aArithExpr -> aArithExpr.accept(this));
        indentLeft();
        formatLine(")");
    }

    @Override
    public void visit(Minus minus) {
        formatLine("(-");
        indentRight();
        minus.getOperands().forEach(aArithExpr -> aArithExpr.accept(this));
        indentLeft();
        formatLine(")");
    }

    @Override
    public void visit(Times times) {
        formatLine("(*");
        indentRight();
        times.getOperands().forEach(aArithExpr -> aArithExpr.accept(this));
        indentLeft();
        formatLine(")");
    }

    @Override
    public void visit(Div div) {
        formatLine("(/");
        indentRight();
        div.getOperands().forEach(aArithExpr -> aArithExpr.accept(this));
        indentLeft();
        formatLine(")");
    }

    @Override
    public void visit(False aFalse) {
        formatLine("false");
    }

    @Override
    public void visit(True aTrue) {
        formatLine("true");
    }

    @Override
    public void visit(Not not) {
        formatLine("(not");
        indentRight();
        not.getOperand().accept(this);
        indentLeft();
        formatLine(")");
    }

    @Override
    public void visit(Or or) {
        if (or.getOperands().isEmpty()) {
            new False().accept(this);
        } else {
            formatLine("(or");
            indentRight();
            or.getOperands().forEach(aBoolExpr -> aBoolExpr.accept(this));
            indentLeft();
            formatLine(")");
        }
    }

    @Override
    public void visit(And and) {
        if (and.getOperands().isEmpty()) {
            new True().accept(this);
        } else {
            formatLine("(and");
            indentRight();
            and.getOperands().forEach(aBoolExpr -> aBoolExpr.accept(this));
            indentLeft();
            formatLine(")");
        }
    }

    @Override
    public void visit(Equals equals) {
        formatLine("(=");
        indentRight();
        equals.getOperands().forEach(aBoolExpr -> aBoolExpr.accept(this));
        indentLeft();
        formatLine(")");
    }

    @Override
    public void visit(LT lt) {
        formatLine("(<");
        indentRight();
        lt.getLeft().accept(this);
        lt.getRight().accept(this);
        indentLeft();
        formatLine(")");
    }

    @Override
    public void visit(LEQ leq) {
        formatLine("(<=");
        indentRight();
        leq.getLeft().accept(this);
        leq.getRight().accept(this);
        indentLeft();
        formatLine(")");
    }

    @Override
    public void visit(GT gt) {
        formatLine("(>");
        indentRight();
        gt.getLeft().accept(this);
        gt.getRight().accept(this);
        indentLeft();
        formatLine(")");
    }

    @Override
    public void visit(GEQ geq) {
        formatLine("(>");
        indentRight();
        geq.getLeft().accept(this);
        geq.getRight().accept(this);
        indentLeft();
        formatLine(")");
    }

    @Override
    public void visit(Implies implies) {
        formatLine("(=>");
        indentRight();
        implies.getCondition().accept(this);
        implies.getThenPart().accept(this);
        indentLeft();
        formatLine(")");
    }

    @Override
    public void visit(Equiv equiv) {
        new And(new Implies(equiv.getLeft(), equiv.getRight()), new Implies(equiv.getRight(), equiv.getLeft())).accept(this);
    }

    @Override
    public void visit(BoolITE boolITE) {
        formatLine("(ite");
        indentRight();
        boolITE.getCondition().accept(this);
        boolITE.getThenPart().accept(this);
        boolITE.getElsePart().accept(this);
        indentLeft();
        formatLine(")");
    }

}

package visitors.exprs;

import langs.exprs.AExpr;
import langs.exprs.arith.*;
import langs.exprs.bool.*;
import visitors.AFormatter;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 13:22
 */
public class ExprFormatter extends AFormatter implements IExprVisitor {

    private ExprFormatter() {
    }

    public static String format(AExpr aExpr) {
        ExprFormatter formatter = new ExprFormatter();
        aExpr.accept(formatter);
        return formatter.getFormatted().toString();
    }

    @Override
    public void visit(Int anInt) {
        format(anInt.getValue().toString());
    }

    @Override
    public void visit(Const aConst) {
        format(aConst.getName());
    }

    @Override
    public void visit(Var var) {
        format(var.getName());
    }

    @Override
    public void visit(Plus plus) {
        format("(");
        join(plus.getOperands(), " + ");
        format(")");
    }

    @Override
    public void visit(Minus minus) {
        format("(");
        join(minus.getOperands(), " - ");
        format(")");
    }

    @Override
    public void visit(Times times) {
        format("(");
        join(times.getOperands(), " * ");
        format(")");
    }

    @Override
    public void visit(Div div) {
        format("(");
        join(div.getOperands(), " / ");
        format(")");
    }

    @Override
    public void visit(False aFalse) {
        format("false");
    }

    @Override
    public void visit(True aTrue) {
        format("true");
    }

    @Override
    public void visit(Not not) {
        format("NOT(");
        not.getOperand().accept(this);
        format(")");
    }

    @Override
    public void visit(Or or) {
        format("OR(");
        join(or.getOperands(), ", ");
        format(")");
    }

    @Override
    public void visit(And and) {
        format("AND(");
        join(and.getOperands(), ", ");
        format(")");
    }

    @Override
    public void visit(Equals equals) {
        join(equals.getOperands(), " = ");
    }

    @Override
    public void visit(LT lt) {
        lt.getLeft().accept(this);
        format(" < ");
        lt.getRight().accept(this);
    }

    @Override
    public void visit(LEQ geq) {
        geq.getLeft().accept(this);
        format(" <= ");
        geq.getRight().accept(this);
    }

    @Override
    public void visit(GT gt) {
        gt.getLeft().accept(this);
        format(" > ");
        gt.getRight().accept(this);
    }

    @Override
    public void visit(GEQ geq) {
        geq.getLeft().accept(this);
        format(" >= ");
        geq.getRight().accept(this);
    }

    @Override
    public void visit(Implies implies) {
        implies.getCondition().accept(this);
        format(" => ");
        implies.getThenPart().accept(this);
    }

    @Override
    public void visit(Equiv equiv) {
        equiv.getLeft().accept(this);
        format(" <==> ");
        equiv.getRight().accept(this);
    }

    @Override
    public void visit(BoolITE boolITE) {
        format("(");
        boolITE.getCondition().accept(this);
        format(" ? ");
        boolITE.getThenPart().accept(this);
        format(" : ");
        boolITE.getElsePart().accept(this);
        format(")");
    }

}

package formatters.eventb.exprs;

import formatters.AFormatter;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.*;
import langs.eventb.exprs.sets.Range;

import java.util.stream.Collectors;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 13:22
 */
public abstract class AExprFormatter extends AFormatter implements IExprVisitor {

    protected AExprFormatter() {
    }

    @Override
    public String visit(Int anInt) {
        return anInt.getValue().toString();
    }

    @Override
    public String visit(Const aConst) {
        return aConst.getName();
    }

    @Override
    public String visit(Var var) {
        return var.getName();
    }

    @Override
    public String visit(Fun fun) {
        return fun.getName() + "(" + fun.getOperand().accept(this) + ")";
    }

    @Override
    public String visit(Plus plus) {
        return plus.getOperands().stream().map(expr -> expr.accept(this)).collect(Collectors.joining(" + ", "(", ")"));
    }

    @Override
    public String visit(Minus minus) {
        return minus.getOperands().stream().map(expr -> expr.accept(this)).collect(Collectors.joining(" - ", "(", ")"));
    }

    @Override
    public String visit(Times times) {
        return times.getOperands().stream().map(expr -> expr.accept(this)).collect(Collectors.joining(" * ", "(", ")"));
    }

    @Override
    public String visit(Div div) {
        return div.getOperands().stream().map(expr -> expr.accept(this)).collect(Collectors.joining(" / ", "(", ")"));
    }

    @Override
    public String visit(False aFalse) {
        return "false";
    }

    @Override
    public String visit(True aTrue) {
        return "true";
    }

    @Override
    public String visit(Not not) {
        return "NOT(" + not.getOperand().accept(this) + ")";
    }

    @Override
    public String visit(Or or) {
        return line("OR(") + indentRight() + or.getOperands().stream().map(operand -> indentLine(operand.accept(this))).collect(Collectors.joining()) + indentLeft() + indent(")");
    }

    @Override
    public String visit(And and) {
        return line("AND(") + indentRight() + and.getOperands().stream().map(operand -> indentLine(operand.accept(this))).collect(Collectors.joining()) + indentLeft() + indent(")");
    }

    @Override
    public String visit(Equals equals) {
        return equals.getOperands().stream().map(operand -> operand.accept(this)).collect(Collectors.joining(" = "));
    }

    @Override
    public String visit(NEQ neq) {
        return new Not(new Equals(neq.getOperands().toArray(new AArithExpr[neq.getOperands().size()]))).accept(this);
    }

    @Override
    public String visit(LT lt) {
        return lt.getLeft().accept(this) + " < " + lt.getRight().accept(this);
    }

    @Override
    public String visit(LEQ leq) {
        return leq.getLeft().accept(this) + " <= " + leq.getRight().accept(this);
    }

    @Override
    public String visit(GT gt) {
        return gt.getLeft().accept(this) + " > " + gt.getRight().accept(this);
    }

    @Override
    public String visit(GEQ geq) {
        return geq.getLeft().accept(this) + " >= " + geq.getRight().accept(this);
    }

    @Override
    public String visit(Implies implies) {
        return implies.getCondition().accept(this) + " => " + implies.getThenPart().accept(this);
    }

    @Override
    public String visit(Equiv equiv) {
        return equiv.getLeft().accept(this) + " <=> " + equiv.getRight().accept(this);
    }

    @Override
    public String visit(BoolITE boolITE) {
        return "(" + boolITE.getCondition().accept(this) + " ? " + boolITE.getThenPart().accept(this) + " : " + boolITE.getElsePart().accept(this) + ")";
    }

    @Override
    public String visit(Exists exists) {
        return "€(" + exists.getQuantifiedVarsDefs().stream().map(tuple -> tuple.getFirst().accept(this) + " in " + tuple.getSecond().accept(this)).collect(Collectors.joining(", ")) + ").(" + exists.getExpr().accept(this) + ")";
    }

    @Override
    public String visit(ForAll forAll) {
        return "!(" + forAll.getQuantifiedVarsDefs().stream().map(tuple -> tuple.getFirst().accept(this) + " in " + tuple.getSecond().accept(this)).collect(Collectors.joining(", ")) + ").(" + forAll.getExpr().accept(this) + ")";
    }

    @Override
    public String visit(Invariant invariant) {
        return invariant.getExpr().accept(this);
    }

    @Override
    public String visit(Range range) {
        return range.getLowerBound().accept(this) + ".." + range.getUpperBound().accept(this);
    }

}

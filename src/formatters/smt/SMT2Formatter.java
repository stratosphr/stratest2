package formatters.smt;

import formatters.AFormatter;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.*;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 11:35
 */
public final class SMT2Formatter extends AFormatter implements ISMT2Visitor {

    private SMT2Formatter() {
    }

    public static String format(ABoolExpr boolExpr) {
        SMT2Formatter formatter = new SMT2Formatter();
        String formatted = "";
        LinkedHashSet<Const> consts = boolExpr.getConsts();
        LinkedHashSet<Var> vars = boolExpr.getVars();
        if (!consts.isEmpty()) {
            formatted += formatter.line("; CONSTS");
            formatted += consts.stream().map(aConst -> formatter.line("(declare-fun " + aConst.getName() + " () Int)")).collect(Collectors.joining());
        }
        if (!vars.isEmpty()) {
            formatted += formatter.line("; VARS");
            formatted += vars.stream().map(var -> formatter.line("(declare-fun " + var.getName() + " () Int)")).collect(Collectors.joining());
        }
        formatted += formatter.line() + formatter.line("(assert") + formatter.indentRight() + formatter.indentLine(boolExpr.accept(formatter)) + formatter.indentLeft() + formatter.indent(")");
        return formatted;
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
    public String visit(Plus plus) {
        return line("(+") + indentRight() + plus.getOperands().stream().map(expr -> indentLine(expr.accept(this))).collect(Collectors.joining()) + indentLeft() + indent(")");
    }

    @Override
    public String visit(Minus minus) {
        return line("(-") + indentRight() + minus.getOperands().stream().map(expr -> indentLine(expr.accept(this))).collect(Collectors.joining()) + indentLeft() + indent(")");
    }

    @Override
    public String visit(Times times) {
        return line("(*") + indentRight() + times.getOperands().stream().map(expr -> indentLine(expr.accept(this))).collect(Collectors.joining()) + indentLeft() + indent(")");
    }

    @Override
    public String visit(Div div) {
        return line("(/") + indentRight() + div.getOperands().stream().map(expr -> indentLine(expr.accept(this))).collect(Collectors.joining()) + indentLeft() + indent(")");
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
        return line("(not") + indentRight() + indentLine(not.getOperand().accept(this)) + indentLeft() + indent(")");
    }

    @Override
    public String visit(Or or) {
        return line("(or") + indentRight() + or.getOperands().stream().map(expr -> indentLine(expr.accept(this))).collect(Collectors.joining()) + indentLeft() + indent(")");
    }

    @Override
    public String visit(And and) {
        return line("(and") + indentRight() + and.getOperands().stream().map(expr -> indentLine(expr.accept(this))).collect(Collectors.joining()) + indentLeft() + indent(")");
    }

    @Override
    public String visit(Equals equals) {
        return line("(=") + indentRight() + equals.getOperands().stream().map(expr -> indentLine(expr.accept(this))).collect(Collectors.joining()) + indentLeft() + indent(")");
    }

    @Override
    public String visit(LT lt) {
        return line("(<") + indentRight() + indentLine(lt.getLeft().accept(this)) + indentLine(lt.getRight().accept(this)) + indentLeft() + indent(")");
    }

    @Override
    public String visit(LEQ leq) {
        return line("(<=") + indentRight() + indentLine(leq.getLeft().accept(this)) + indentLine(leq.getRight().accept(this)) + indentLeft() + indent(")");
    }

    @Override
    public String visit(GT gt) {
        return line("(>") + indentRight() + indentLine(gt.getLeft().accept(this)) + indentLine(gt.getRight().accept(this)) + indentLeft() + indent(")");
    }

    @Override
    public String visit(GEQ geq) {
        return line("(>=") + indentRight() + indentLine(geq.getLeft().accept(this)) + indentLine(geq.getRight().accept(this)) + indentLeft() + indent(")");
    }

    @Override
    public String visit(Implies implies) {
        return line("(=>") + indentRight() + indentLine(implies.getCondition().accept(this)) + indentLine(implies.getThenPart().accept(this)) + indentLeft() + indent(")");
    }

    @Override
    public String visit(Equiv equiv) {
        return new And(new Implies(equiv.getLeft(), equiv.getRight()), new Implies(equiv.getRight(), equiv.getLeft())).accept(this);
    }

    @Override
    public String visit(BoolITE boolITE) {
        return line("(ite") + indentRight() + indentLine(boolITE.getCondition().accept(this)) + indentLine(boolITE.getThenPart().accept(this)) + indentLine(boolITE.getElsePart().accept(this)) + indentLeft() + indent(")");
    }

    @Override
    public String visit(Exists exists) {
        return line("(exists") + indentRight() + indentLine("(") + indentRight() + exists.getQuantifiedVars().stream().map(var -> indentLine("(" + var.getName() + " Int)")).collect(Collectors.joining()) + indentLeft() + indentLine(")") + indentLine(exists.getExpr().accept(this)) + indentLeft() + indent(")");
    }

    @Override
    public String visit(ForAll forAll) {
        return line("(forall") + indentRight() + indentLine("(") + indentRight() + forAll.getQuantifiedVars().stream().map(var -> indentLine("(" + var.getName() + " Int)")).collect(Collectors.joining()) + indentLeft() + indentLine(")") + indentLine(forAll.getExpr().accept(this)) + indentLeft() + indent(")");
    }

}

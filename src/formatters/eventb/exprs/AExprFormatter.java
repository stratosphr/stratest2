package formatters.eventb.exprs;

import formatters.AFormatter;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.*;
import langs.eventb.exprs.bool.GEQ;
import langs.eventb.exprs.bool.LEQ;
import langs.eventb.exprs.bool.NEQ;
import langs.eventb.exprs.sets.Enum;
import langs.eventb.exprs.sets.NamedSet;
import langs.eventb.exprs.sets.Range;
import langs.eventb.exprs.sets.Set;

import java.util.stream.Collectors;

import static utilities.Chars.*;

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
    public String visit(ArithITE arithITE) {
        return "(" + arithITE.getCondition().accept(this) + " ? " + arithITE.getThenPart().accept(this) + " : " + arithITE.getElsePart().accept(this) + ")";
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
        return "(" + not.getOperand().accept(this) + ")";
    }

    @Override
    public String visit(Or or) {
        return or.getOperands().isEmpty() ? new False().accept(this) : line("or(") + indentRight() + or.getOperands().stream().map(operand -> indentLine(operand.accept(this))).collect(Collectors.joining()) + indentLeft() + indent(")");
    }

    @Override
    public String visit(And and) {
        return and.getOperands().isEmpty() ? new True().accept(this) : line("and(") + indentRight() + and.getOperands().stream().map(operand -> indentLine(operand.accept(this))).collect(Collectors.joining()) + indentLeft() + indent(")");
    }

    @Override
    public String visit(Equals equals) {
        return equals.getOperands().stream().map(operand -> operand.accept(this)).collect(Collectors.joining(" = "));
    }

    @Override
    public String visit(NEQ neq) {
        return neq.getLeft().accept(this) + " " + NEQ + " " + neq.getRight().accept(this);
    }

    @Override
    public String visit(LT lt) {
        return lt.getLeft().accept(this) + " < " + lt.getRight().accept(this);
    }

    @Override
    public String visit(LEQ leq) {
        return leq.getLeft().accept(this) + " " + LEQ + " " + leq.getRight().accept(this);
    }

    @Override
    public String visit(GT gt) {
        return gt.getLeft().accept(this) + " > " + gt.getRight().accept(this);
    }

    @Override
    public String visit(GEQ geq) {
        return geq.getLeft().accept(this) + " " + GEQ + " " + geq.getRight().accept(this);
    }

    @Override
    public String visit(Implies implies) {
        return implies.getCondition().accept(this) + " " + IMPLIES + " " + implies.getThenPart().accept(this);
    }

    @Override
    public String visit(Equiv equiv) {
        return equiv.getLeft().accept(this) + " " + EQUIV + " " + equiv.getRight().accept(this);
    }

    @Override
    public String visit(BoolITE boolITE) {
        return "(" + boolITE.getCondition().accept(this) + " ? " + boolITE.getThenPart().accept(this) + " : " + boolITE.getElsePart().accept(this) + ")";
    }

    @Override
    public String visit(Exists exists) {
        return EXISTS + "(" + exists.getQuantifiedVarsDefs().stream().map(tuple -> tuple.getFirst().accept(this) + " " + IN + " " + tuple.getSecond().accept(this)).collect(Collectors.joining(", ")) + ").(" + exists.getExpr().accept(this) + ")";
    }

    @Override
    public String visit(ForAll forAll) {
        return FORALL + "(" + forAll.getQuantifiedVarsDefs().stream().map(tuple -> tuple.getFirst().accept(this) + " " + IN + " " + tuple.getSecond().accept(this)).collect(Collectors.joining(", ")) + ").(" + forAll.getExpr().accept(this) + ")";
    }

    @Override
    public String visit(Invariant invariant) {
        return invariant.getExpr().accept(this);
    }

    @Override
    public String visit(InDomain inDomain) {
        return inDomain.getExpr().accept(this) + " " + IN + " " + inDomain.getDomain().accept(this);
    }

    @Override
    public String visit(Set set) {
        return "{" + set.getSet().stream().map(value -> value.accept(this)).collect(Collectors.joining(", ")) + "}";
    }

    @Override
    public String visit(Enum anEnum) {
        return "{" + anEnum.getEnumValues().stream().map(enumValue -> enumValue.accept(this)).collect(Collectors.joining(", ")) + "}";
    }

    @Override
    public String visit(NamedSet namedSet) {
        return namedSet.getName();
    }

    @Override
    public String visit(Range range) {
        return range.getLowerBound().accept(this) + ".." + range.getUpperBound().accept(this);
    }

    @Override
    public String visit(EnumValue enumValue) {
        return enumValue.getName();
    }

}

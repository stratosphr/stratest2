package formatters.smt;

import formatters.AFormatter;
import graphs.ConcreteState;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.*;
import langs.eventb.exprs.sets.ASetExpr;
import utilities.sets.Tuple2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 11:35
 */
public final class SMT2Formatter extends AFormatter implements ISMT2Visitor {

    private SMT2Formatter() {
    }

    public static String format(ABoolExpr boolExpr) {
        SMT2Formatter formatter = new SMT2Formatter();
        StringBuilder formatted = new StringBuilder();
        LinkedHashSet<Const> consts = boolExpr.getConsts();
        LinkedHashSet<Var> vars = boolExpr.getVars();
        LinkedHashSet<String> funsNames = boolExpr.getFuns().stream().map(AAssignable::getName).collect(Collectors.toCollection(LinkedHashSet::new));
        if (!consts.isEmpty()) {
            formatted.append(formatter.line("; CONSTS"));
            formatted.append(consts.stream().map(aConst -> formatter.line("(declare-fun " + aConst.getName() + " () Int)")).collect(Collectors.joining()));
            formatted.append(formatter.line());
        }
        if (!vars.isEmpty()) {
            formatted.append(formatter.line("; VARS"));
            formatted.append(vars.stream().map(var -> formatter.line("(declare-fun " + var.getName() + " () Int)")).collect(Collectors.joining()));
            formatted.append(formatter.line());
        }
        if (!funsNames.isEmpty()) {
            formatted.append(formatter.line("; FUNS"));
            for (String funName : funsNames) {
                formatted.append(formatter.line("(define-fun read_" + funName + " ((index" + Fun.getParameterDelimiter() + " Int)) Int"));
                Tuple2<ASetExpr, ASetExpr> funDomains = Machine.getFunsDefs().get(funName);
                ArrayList<AValue> funDomain = new ArrayList<>(funDomains.getFirst().getSet());
                ArrayList<AValue> funCoDomain = new ArrayList<>(funDomains.getSecond().getSet());
                List<ABoolExpr> equals = funDomain.stream().map(value -> new Equals(new Var("index" + Fun.getParameterDelimiter()), value)).collect(Collectors.toList());
                Collections.reverse(equals);
                ArithITE ite = new ArithITE(
                        equals.get(0),
                        new Var(funName + Fun.getParameterDelimiter() + funDomain.get(funDomain.size() - 1)),
                        new Minus(funCoDomain.iterator().next(), new Int(1))
                );
                for (int i = 1; i < equals.subList(1, equals.size()).size() + 1; i++) {
                    ite = new ArithITE(equals.get(i), new Var(funName + Fun.getParameterDelimiter() + funDomain.get(funDomain.size() - 1 - i)), ite);
                }
                formatted.append(formatter.indentRight()).append(formatter.indentLine(ite.accept(formatter))).append(formatter.indentLeft());
                formatted.append(formatter.indent(")"));
                formatted.append(formatter.line());
            }
        }
        formatted.append(formatter.line()).append(formatter.line("(assert")).append(formatter.indentRight()).append(formatter.indentLine(boolExpr.accept(formatter))).append(formatter.indentLeft()).append(formatter.indent(")"));
        return formatted.toString();
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
        return line("(read_" + fun.getName()) + indentRight() + indentLine(fun.getOperand().accept(this)) + indentLeft() + indent(")");
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
    public String visit(ArithITE arithITE) {
        return line("(ite") + indentRight() + indentLine(arithITE.getCondition().accept(this)) + indentLine(arithITE.getThenPart().accept(this)) + indentLine(arithITE.getElsePart().accept(this)) + indentLeft() + indent(")");
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
        return or.getOperands().isEmpty() ? new False().accept(this) : line("(or") + indentRight() + or.getOperands().stream().map(expr -> indentLine(expr.accept(this))).collect(Collectors.joining()) + indentLeft() + indent(")");
    }

    @Override
    public String visit(And and) {
        return and.getOperands().isEmpty() ? new True().accept(this) : line("(and") + indentRight() + and.getOperands().stream().map(expr -> indentLine(expr.accept(this))).collect(Collectors.joining()) + indentLeft() + indent(")");
    }

    @Override
    public String visit(Equals equals) {
        return line("(=") + indentRight() + equals.getOperands().stream().map(expr -> indentLine(expr.accept(this))).collect(Collectors.joining()) + indentLeft() + indent(")");
    }

    @Override
    public String visit(NEQ neq) {
        return new Not(new Equals(neq.getLeft(), neq.getRight())).accept(this);
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
        And and = new And(Stream.of(exists.getQuantifiedVarsDefs().stream().map(tuple -> new InDomain(tuple.getFirst(), tuple.getSecond())).collect(Collectors.toList()), Collections.singletonList(exists.getExpr())).flatMap(Collection::stream).toArray(ABoolExpr[]::new));
        return line("(exists") + indentRight() + indentLine("(") + indentRight() + exists.getQuantifiedVars().stream().map(var -> indentLine("(" + var.getName() + " Int)")).collect(Collectors.joining()) + indentLeft() + indentLine(")") + indentLine(and.accept(this)) + indentLeft() + indent(")");
    }

    @Override
    public String visit(ForAll forAll) {
        Implies implies = new Implies(new And(forAll.getQuantifiedVarsDefs().stream().map(tuple -> new InDomain(tuple.getFirst(), tuple.getSecond())).toArray(ABoolExpr[]::new)), forAll.getExpr());
        return line("(forall") + indentRight() + indentLine("(") + indentRight() + forAll.getQuantifiedVars().stream().map(var -> indentLine("(" + var.getName() + " Int)")).collect(Collectors.joining()) + indentLeft() + indentLine(")") + indentLine(implies.accept(this)) + indentLeft() + indent(")");
    }

    @Override
    public String visit(Invariant invariant) {
        return invariant.getExpr().accept(this);
    }

    @Override
    public String visit(InDomain inDomain) {
        return new Or(inDomain.getDomain().getSet().stream().map(value -> new Equals(inDomain.getExpr(), value)).toArray(ABoolExpr[]::new)).accept(this);
    }

    @Override
    public String visit(EnumValue enumValue) {
        return enumValue.getValue().accept(this);
    }

    @Override
    public String visit(Predicate predicate) {
        return predicate.getExpr().accept(this);
    }

    @Override
    public String visit(ConcreteState concreteState) {
        return concreteState.getExpr().accept(this);
    }

}

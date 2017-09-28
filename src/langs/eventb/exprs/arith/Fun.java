package langs.eventb.exprs.arith;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.Machine;
import visitors.primer.IPrimerVisitor;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 21/09/17.
 * Time : 10:38
 */
public final class Fun extends AAssignable {

    private final AArithExpr operand;

    public Fun(String name, AArithExpr operand) {
        super(name);
        this.operand = operand;
    }

    public static String getParameterDelimiter() {
        return "!";
    }

    @Override
    public String accept(ISMT2Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Fun accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return operand.getConsts();
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return new LinkedHashSet<>(Machine.getFunsDefs().get(name).getFirst().getSet().stream().map(value -> new Var(name + Fun.getParameterDelimiter() + value)).collect(Collectors.toList()));
    }

    @Override
    public LinkedHashSet<Fun> getFuns() {
        return new LinkedHashSet<>(Collections.singletonList(this));
    }

    public AArithExpr getOperand() {
        return operand;
    }

    @Override
    public AAssignable clone() {
        return new Fun(name, operand.clone());
    }

}

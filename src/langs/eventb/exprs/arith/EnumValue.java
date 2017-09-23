package langs.eventb.exprs.arith;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import visitors.primer.IPrimerVisitor;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 23/09/17.
 * Time : 03:07
 */
public final class EnumValue extends AValue {

    private static int uniqueID = 0;
    private String name;
    private final Int value;

    public EnumValue(String name, Int value) {
        this.name = name;
        this.value = value;
        uniqueID++;
    }

    public static Int getUniqueID() {
        return new Int(uniqueID);
    }

    @Override
    public String accept(ISMT2Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public EnumValue accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<Const> getConsts() {
        return new LinkedHashSet<>();
    }

    @Override
    public LinkedHashSet<Var> getVars() {
        return new LinkedHashSet<>();
    }

    @Override
    public LinkedHashSet<Fun> getFuns() {
        return new LinkedHashSet<>();
    }

    public String getName() {
        return name;
    }

    public Int getValue() {
        return value;
    }

}

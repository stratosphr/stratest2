package langs.eventb.exprs.arith;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import visitors.primer.IPrimerVisitor;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 23/09/17.
 * Time : 03:07
 */
public final class EnumValue extends AValue {

    private final static LinkedHashMap<String, Int> mapping = new LinkedHashMap<>();
    private final static LinkedHashMap<Int, String> reversedMapping = new LinkedHashMap<>();
    private static int uniqueID = 0;
    private final String name;

    public EnumValue(String name) {
        this.name = name;
        if (!mapping.containsKey(name)) {
            mapping.put(name, new Int(uniqueID));
            reversedMapping.put(new Int(uniqueID), name);
            ++uniqueID;
        }
    }

    public static LinkedHashMap<Int, String> getReversedMapping() {
        return reversedMapping;
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
        return mapping.get(name);
    }

}

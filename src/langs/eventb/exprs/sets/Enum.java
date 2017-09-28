package langs.eventb.exprs.sets;

import formatters.eventb.exprs.IExprVisitor;
import langs.eventb.exprs.arith.*;
import visitors.primer.IPrimerVisitor;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 23/09/17.
 * Time : 02:51
 */
public final class Enum extends ASetExpr {

    private final List<EnumValue> enumValues;

    public Enum(EnumValue... enumValues) {
        this.enumValues = Arrays.asList(enumValues);
    }

    @Override
    public Enum accept(IPrimerVisitor visitor) {
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

    @Override
    public ASetExpr clone() {
        return new Enum(enumValues.stream().map(AValue::clone).toArray(EnumValue[]::new));
    }

    @Override
    public LinkedHashSet<AValue> getSet() {
        return enumValues.stream().map(EnumValue::getValue).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public AValue retrieveValue(Int value) {
        return new EnumValue(EnumValue.getReversedMapping().get(value));
    }

    public List<EnumValue> getEnumValues() {
        return enumValues;
    }

}

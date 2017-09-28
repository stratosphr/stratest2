package graphs;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.arith.AValue;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.And;
import langs.eventb.exprs.bool.Equals;
import visitors.primer.IPrimerVisitor;

import java.util.TreeMap;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 16:03
 */
public final class ConcreteState extends AState<AAssignable, AValue> {

    public ConcreteState(String name, TreeMap<AAssignable, AValue> mapping) {
        super(name, new And(mapping.keySet().stream().map(assignable -> new Equals(assignable, mapping.get(assignable))).toArray(ABoolExpr[]::new)), mapping);
    }

    @Override
    public String accept(ISMT2Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public ConcreteState accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public ConcreteState clone() {
        TreeMap<AAssignable, AValue> mapping = new TreeMap<>();
        this.mapping.forEach((assignable, value) -> mapping.put(assignable.clone(), value.clone()));
        ConcreteState clone = new ConcreteState(name, mapping);
        markers.forEach(clone::setMarker);
        return clone;
    }

    public void setName(String name) {
        this.name = name;
    }

}

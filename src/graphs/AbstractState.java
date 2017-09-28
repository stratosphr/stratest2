package graphs;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.And;
import langs.eventb.exprs.bool.Not;
import visitors.primer.IPrimerVisitor;

import java.util.TreeMap;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 16:03
 */
public final class AbstractState extends AState<ABoolExpr, Boolean> {

    public AbstractState(String name, TreeMap<ABoolExpr, Boolean> mapping) {
        super(name, new And(mapping.keySet().stream().map(predicate -> mapping.get(predicate) ? predicate : new Not(predicate)).toArray(ABoolExpr[]::new)), mapping);
    }

    @Override
    public String accept(ISMT2Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public AbstractState accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public AbstractState clone() {
        TreeMap<ABoolExpr, Boolean> mapping = new TreeMap<>();
        this.mapping.forEach((expr1, aBoolean) -> mapping.put(expr1.clone(), aBoolean));
        AbstractState clone = new AbstractState(name, mapping);
        markers.forEach(clone::setMarker);
        return clone;
    }

}

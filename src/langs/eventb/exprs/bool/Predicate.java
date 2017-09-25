package langs.eventb.exprs.bool;

import formatters.eventb.exprs.IExprVisitor;
import formatters.smt.ISMT2Visitor;
import visitors.primer.IPrimerVisitor;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 16:03
 */
public final class Predicate extends APredicate {

    public Predicate(String name, ABoolExpr expr) {
        super(name, expr);
    }

    @Override
    public String accept(ISMT2Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Predicate accept(IPrimerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExprVisitor visitor) {
        return visitor.visit(this);
    }

}

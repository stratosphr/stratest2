package langs.exprs.bool;

import visitors.smt.ISMT2Visitor;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:20
 */
public final class False extends ABoolExpr {

    @Override
    public void accept(ISMT2Visitor visitor) {
        visitor.visit(this);
    }

}

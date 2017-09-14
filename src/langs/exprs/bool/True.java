package langs.exprs.bool;

import visitors.smt.ISMT2Visitor;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:08
 */
public final class True extends ABoolExpr {

    @Override
    public void accept(ISMT2Visitor visitor) {
        visitor.visit(this);
    }

}

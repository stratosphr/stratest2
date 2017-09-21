package langs.eventb.exprs.arith;

import formatters.smt.ISMT2Visitable;
import langs.eventb.exprs.AExpr;
import visitors.primer.Primer;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:43
 */
public abstract class AArithExpr extends AExpr<AArithExpr> implements ISMT2Visitable {

    @Override
    public AArithExpr prime() {
        return Primer.prime(this);
    }

    @Override
    public AArithExpr unprime() {
        return Primer.unprime(this);
    }

}

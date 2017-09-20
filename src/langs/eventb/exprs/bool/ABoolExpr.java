package langs.eventb.exprs.bool;

import langs.eventb.exprs.AExpr;
import visitors.primer.Primer;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 11:33
 */
public abstract class ABoolExpr extends AExpr<ABoolExpr> {

    @Override
    public ABoolExpr prime() {
        return Primer.prime(this);
    }

    @Override
    public ABoolExpr unprime() {
        return Primer.unprime(this);
    }

}

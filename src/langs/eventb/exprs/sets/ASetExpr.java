package langs.eventb.exprs.sets;

import langs.eventb.exprs.AExpr;
import langs.eventb.exprs.arith.AValue;
import langs.eventb.exprs.arith.Int;
import visitors.primer.Primer;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 21/09/17.
 * Time : 10:24
 */
public abstract class ASetExpr extends AExpr<ASetExpr> {

    @Override
    public ASetExpr prime() {
        return Primer.prime(this);
    }

    @Override
    public ASetExpr unprime() {
        return Primer.unprime(this);
    }

    public abstract LinkedHashSet<AValue> getSet();

    public abstract AValue retrieveValue(Int value);

}

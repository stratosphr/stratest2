package langs.eventb.exprs.arith;

/**
 * Created by gvoiron on 15/09/17.
 * Time : 15:55
 */
public abstract class AAssignable extends AArithExpr {

    protected final String name;

    public AAssignable(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

}

package langs.exprs.arith;

/**
 * Created by gvoiron on 15/09/17.
 * Time : 15:55
 */
public abstract class AAssignable extends AArithExpr {

    private String name;

    public AAssignable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

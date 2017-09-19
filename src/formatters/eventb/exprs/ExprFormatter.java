package formatters.eventb.exprs;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 13:22
 */
public final class ExprFormatter extends AExprFormatter implements IExprVisitor {

    private ExprFormatter() {
    }

    public static String format(IExprVisitable visitable) {
        ExprFormatter formatter = new ExprFormatter();
        return visitable.accept(formatter);
    }

}

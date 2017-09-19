package formatters.exprs;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 13:23
 */
public interface IExprVisitable {

    void accept(IExprVisitor visitor);

}

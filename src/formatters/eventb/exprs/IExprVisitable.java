package formatters.eventb.exprs;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 13:23
 */
public interface IExprVisitable {

    String accept(IExprVisitor visitor);

}

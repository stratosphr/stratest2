package formatters.eventb;

import formatters.eventb.exprs.IExprVisitor;
import langs.eventb.substitutions.Assignment;
import langs.eventb.substitutions.IfThenElse;
import langs.eventb.substitutions.Select;
import langs.eventb.substitutions.Skip;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 10:42
 */
public interface IEventBVisitor extends IExprVisitor {

    String visit(Skip skip);

    String visit(Assignment assignment);

    String visit(Select select);

    String visit(IfThenElse ifThenElse);

}

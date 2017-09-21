package formatters.eventb;

import formatters.eventb.exprs.IExprVisitor;
import langs.eventb.Event;
import langs.eventb.Machine;
import langs.eventb.substitutions.*;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 10:42
 */
public interface IEventBVisitor extends IExprVisitor {

    String visit(Machine machine);

    String visit(Event event);

    String visit(Skip skip);

    String visit(Assignment assignment);

    String visit(Assignments assignments);

    String visit(Select select);

    String visit(IfThenElse ifThenElse);

    String visit(Any any);

}

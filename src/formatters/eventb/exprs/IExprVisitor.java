package formatters.eventb.exprs;

import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.*;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 13:22
 */
public interface IExprVisitor {

    String visit(Int anInt);

    String visit(Const aConst);

    String visit(Var var);

    String visit(Plus plus);

    String visit(Minus minus);

    String visit(Times times);

    String visit(Div div);

    String visit(False aFalse);

    String visit(True aTrue);

    String visit(Not not);

    String visit(Or or);

    String visit(And and);

    String visit(Equals equals);

    String visit(LT lt);

    String visit(LEQ leq);

    String visit(GT gt);

    String visit(GEQ geq);

    String visit(Implies implies);

    String visit(Equiv equiv);

    String visit(BoolITE boolITE);

    String visit(Exists exists);

    String visit(ForAll forAll);

}
package visitors.exprs;

import langs.exprs.arith.*;
import langs.exprs.bool.*;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 13:22
 */
public interface IExprVisitor {

    void visit(Int anInt);

    void visit(Const aConst);

    void visit(Var var);

    void visit(Plus plus);

    void visit(Minus minus);

    void visit(Times times);

    void visit(Div div);

    void visit(False aFalse);

    void visit(True aTrue);

    void visit(Not not);

    void visit(Or or);

    void visit(And and);

    void visit(Equals equals);

    void visit(LT lt);

    void visit(LEQ geq);

    void visit(GT gt);

    void visit(GEQ geq);

    void visit(Implies implies);

    void visit(Equiv equiv);

    void visit(BoolITE boolITE);

}

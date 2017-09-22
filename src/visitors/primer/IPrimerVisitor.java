package visitors.primer;

import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.*;
import langs.eventb.exprs.sets.ASetExpr;
import langs.eventb.exprs.sets.Range;

/**
 * Created by gvoiron on 20/09/17.
 * Time : 14:50
 */
public interface IPrimerVisitor {

    Int visit(Int anInt);

    Const visit(Const aConst);

    Var visit(Var var);

    Fun visit(Fun fun);

    Plus visit(Plus plus);

    Minus visit(Minus minus);

    Times visit(Times times);

    Div visit(Div div);

    ArithITE visit(ArithITE arithITE);

    False visit(False aFalse);

    True visit(True aTrue);

    Not visit(Not not);

    Or visit(Or or);

    And visit(And and);

    Equals visit(Equals equals);

    NEQ visit(NEQ neq);

    LT visit(LT lt);

    LEQ visit(LEQ leq);

    GT visit(GT gt);

    GEQ visit(GEQ geq);

    Implies visit(Implies implies);

    Equiv visit(Equiv equiv);

    BoolITE visit(BoolITE boolITE);

    Exists visit(Exists exists);

    ForAll visit(ForAll forAll);

    ABoolExpr visit(Invariant invariant);

    ASetExpr visit(Range range);

    InDomain visit(InDomain inDomain);

}

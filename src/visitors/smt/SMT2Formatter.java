package visitors.smt;

import langs.exprs.bool.ABoolExpr;
import langs.exprs.bool.False;
import langs.exprs.bool.True;
import visitors.AFormatter;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 11:35
 */
public final class SMT2Formatter extends AFormatter<ABoolExpr> implements ISMT2Visitor {

    private SMT2Formatter() {
    }

    public static String format(ABoolExpr boolExpr) {
        SMT2Formatter formatter = new SMT2Formatter();
        formatter.formatLine("(assert");
        formatter.indentRight();
        boolExpr.accept(formatter);
        formatter.indentLeft();
        formatter.formatLine(")");
        return formatter.getFormatted().toString();
    }

    @Override
    public void visit(False aFalse) {
        formatLine("false");
    }

    @Override
    public void visit(True aTrue) {
        formatLine("true");
    }

}

package visitors.smt;

import langs.exprs.bool.False;
import langs.exprs.bool.True;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:11
 */
public interface ISMT2Visitor {

    void visit(False aFalse);

    void visit(True aTrue);

}

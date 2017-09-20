package langs.eventb.substitutions;

import langs.eventb.AEventBObject;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.bool.ABoolExpr;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 20:48
 */
public abstract class ASubstitution extends AEventBObject {

    public abstract ABoolExpr getPrd(LinkedHashSet<AAssignable> assignables);

}

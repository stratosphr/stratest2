package langs.eventb.substitutions;

import formatters.eventb.IEventBVisitor;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 22:01
 */
public final class Skip extends ASubstitution {

    @Override
    public String accept(IEventBVisitor visitor) {
        return visitor.visit(this);
    }

}

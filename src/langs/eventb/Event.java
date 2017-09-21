package langs.eventb;

import formatters.eventb.IEventBVisitor;
import langs.eventb.substitutions.ASubstitution;

/**
 * Created by gvoiron on 21/09/17.
 * Time : 18:21
 */
public final class Event extends AEventBObject {

    private final String name;
    private final ASubstitution substitution;

    public Event(String name, ASubstitution substitution) {
        this.name = name;
        this.substitution = substitution;
    }

    @Override
    public String accept(IEventBVisitor visitor) {
        return visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public ASubstitution getSubstitution() {
        return substitution;
    }

}

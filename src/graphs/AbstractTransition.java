package graphs;

import langs.eventb.Event;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 18:24
 */
public final class AbstractTransition extends ATransition<AbstractState, Event> {

    public AbstractTransition(AbstractState source, Event label, AbstractState target) {
        super(source, label, target);
    }

    public Event getEvent() {
        return getLabel();
    }

    @Override
    public String toString() {
        return source + " -[ " + label.getName() + " ]-> " + target;
    }

    @Override
    public AbstractTransition clone() {
        return new AbstractTransition(source.clone(), label.clone(), target.clone());
    }

}

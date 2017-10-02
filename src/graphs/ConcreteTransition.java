package graphs;

import langs.eventb.Event;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 17:04
 */
public class ConcreteTransition extends ATransition<ConcreteState, Event> {

    public ConcreteTransition(ConcreteState source, Event event, ConcreteState target) {
        super(source, event, target);
    }

    public Event getEvent() {
        return getLabel();
    }

    @Override
    public String toString() {
        return source + " -[ " + getEvent().getName() + " ]-> " + target;
    }

    @Override
    public ConcreteTransition clone() {
        return new ConcreteTransition(source.clone(), getEvent().clone(), target.clone());
    }

}

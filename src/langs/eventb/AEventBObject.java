package langs.eventb;

import formatters.eventb.EventBFormatter;
import formatters.eventb.IEventBVisitable;
import utilities.ICloneable;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 10:54
 */
public abstract class AEventBObject<T extends AEventBObject> implements IEventBVisitable, ICloneable<T>, Comparable<AEventBObject> {

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return getClass().getName().equals(o.getClass().getName()) && toString().equals(o.toString());
    }

    @Override
    public int compareTo(AEventBObject aEventBObject) {
        return toString().compareTo(aEventBObject.toString());
    }

    @Override
    public String toString() {
        return EventBFormatter.format(this);
    }

    @Override
    public abstract T clone();

}

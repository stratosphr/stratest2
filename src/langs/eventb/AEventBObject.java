package langs.eventb;

import formatters.eventb.EventBFormatter;
import formatters.eventb.IEventBVisitable;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 10:54
 */
public abstract class AEventBObject implements IEventBVisitable {

    @Override
    public String toString() {
        return EventBFormatter.format(this);
    }

}

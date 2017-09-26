package algs.heuristics;

import langs.eventb.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by gvoiron on 28/06/17.
 * Time : 14:44
 */
public final class DefaultEventsOrderingFunction implements IEventsOrderingFunction {

    @Override
    public LinkedHashSet<Event> apply(LinkedHashSet<Event> events) {
        List<Event> unorderedEvents = new ArrayList<>(events);
        Collections.sort(unorderedEvents);
        return new LinkedHashSet<>(unorderedEvents);
    }

}

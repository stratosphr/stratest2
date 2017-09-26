package algs.heuristics;

import langs.eventb.Event;

import java.util.LinkedHashSet;
import java.util.function.Function;

/**
 * Created by gvoiron on 28/06/17.
 * Time : 14:42
 */
public interface IEventsOrderingFunction extends Function<LinkedHashSet<Event>, LinkedHashSet<Event>> {
}

package algs.outputs;

import graphs.AFSM;
import graphs.AbstractState;
import graphs.AbstractTransition;
import langs.eventb.Event;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 18/06/17.
 * Time : 20:52
 */
public final class MTS extends AFSM<AbstractState, Event> {

    private final LinkedHashSet<AbstractState> q0;
    private final LinkedHashSet<AbstractState> q;
    private final LinkedHashSet<AbstractTransition> delta;
    private final LinkedHashSet<AbstractTransition> deltaMinus;
    private final LinkedHashSet<AbstractTransition> deltaPlus;

    public MTS(LinkedHashSet<AbstractState> q0, LinkedHashSet<AbstractState> q, LinkedHashSet<AbstractTransition> delta, LinkedHashSet<AbstractTransition> deltaMinus, LinkedHashSet<AbstractTransition> deltaPlus) {
        super(q0, q, delta);
        this.q0 = q0;
        this.q = q;
        this.delta = delta;
        this.deltaMinus = deltaMinus;
        this.deltaPlus = deltaPlus;
    }

    public LinkedHashSet<AbstractState> getQ0() {
        return q0;
    }

    public LinkedHashSet<AbstractState> getQ() {
        return q;
    }

    public LinkedHashSet<AbstractTransition> getDelta() {
        return delta;
    }

    public LinkedHashSet<AbstractTransition> getDeltaMinus() {
        return deltaMinus;
    }

    public LinkedHashSet<AbstractTransition> getDeltaPlus() {
        return deltaPlus;
    }

    @Override
    public MTS clone() {
        return new MTS(new LinkedHashSet<>(q0.stream().map(AbstractState::clone).collect(Collectors.toList())), new LinkedHashSet<>(q.stream().map(AbstractState::clone).collect(Collectors.toList())), new LinkedHashSet<>(delta.stream().map(AbstractTransition::clone).collect(Collectors.toList())), new LinkedHashSet<>(deltaMinus.stream().map(AbstractTransition::clone).collect(Collectors.toList())), new LinkedHashSet<>(deltaPlus.stream().map(AbstractTransition::clone).collect(Collectors.toList())));
    }

}

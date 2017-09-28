package algs.outputs;

import algs.heuristics.EConcreteStateColor;
import graphs.AFSM;
import graphs.AbstractState;
import graphs.ConcreteState;
import graphs.ConcreteTransition;
import langs.eventb.Event;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 18/06/17.
 * Time : 20:52
 */
public final class CTS extends AFSM<ConcreteState, Event> {

    private final LinkedHashMap<ConcreteState, AbstractState> alpha;
    private final LinkedHashMap<ConcreteState, EConcreteStateColor> kappa;

    public CTS(LinkedHashSet<ConcreteState> c0, LinkedHashSet<ConcreteState> c, LinkedHashMap<ConcreteState, AbstractState> alpha, LinkedHashMap<ConcreteState, EConcreteStateColor> kappa, LinkedHashSet<ConcreteTransition> deltaC) {
        super(c0, c, deltaC);
        this.alpha = alpha;
        this.kappa = kappa;
    }

    public LinkedHashSet<ConcreteState> getC0() {
        return getInitialStates();
    }

    public LinkedHashSet<ConcreteState> getC() {
        return getStates();
    }

    public LinkedHashMap<ConcreteState, AbstractState> getAlpha() {
        return alpha;
    }

    public LinkedHashMap<ConcreteState, EConcreteStateColor> getKappa() {
        return kappa;
    }

    @SuppressWarnings("unchecked")
    public LinkedHashSet<ConcreteTransition> getDeltaC() {
        return (LinkedHashSet<ConcreteTransition>) getTransitions();
    }

    @Override
    public CTS clone() {
        LinkedHashMap<ConcreteState, AbstractState> alpha = new LinkedHashMap<>();
        LinkedHashMap<ConcreteState, EConcreteStateColor> kappa = new LinkedHashMap<>();
        this.alpha.forEach((concreteState, abstractState) -> alpha.put(concreteState.clone(), abstractState.clone()));
        this.kappa.forEach((concreteState, color) -> kappa.put(concreteState.clone(), color));
        return new CTS(new LinkedHashSet<>(getC0().stream().map(ConcreteState::clone).collect(Collectors.toList())), new LinkedHashSet<>(getC().stream().map(ConcreteState::clone).collect(Collectors.toList())), alpha, kappa, new LinkedHashSet<>(getDeltaC().stream().map(ConcreteTransition::clone).collect(Collectors.toList())));
    }

}

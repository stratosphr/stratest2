package algs;

import com.microsoft.z3.Status;
import graphs.ConcreteState;
import graphs.ConcreteTransition;
import graphs.FSM;
import langs.eventb.Event;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.arith.AValue;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.And;
import langs.eventb.exprs.bool.Not;
import langs.eventb.exprs.bool.Or;
import solvers.z3.Model;
import solvers.z3.Z3;

import java.util.LinkedHashSet;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 15:47
 */
public final class FullFSMComputer extends AComputer<FSM<ConcreteState, Event>> {


    @Override
    protected FSM<ConcreteState, Event> run() {
        LinkedHashSet<ConcreteState> initialStates = new LinkedHashSet<>();
        LinkedHashSet<ConcreteState> states = new LinkedHashSet<>();
        LinkedHashSet<ConcreteTransition> transitions = new LinkedHashSet<>();
        while (Z3.checkSAT(new And(
                Machine.getInvariant(),
                Machine.getInvariant().prime(),
                Machine.getInitialisation().getPrd(Machine.getAssignables()),
                new Not(new Or(states.toArray(new ABoolExpr[states.size()]))).prime()
        )) == Status.SATISFIABLE) {
            Model model = Z3.getModel(Machine.getAssignables().stream().map(assignable -> (AAssignable) assignable.prime()).collect(Collectors.toCollection(LinkedHashSet::new)));
            ConcreteState c0 = concreteState(states, model);
            initialStates.add(c0);
            states.add(c0);
        }
        LinkedHashSet<ConcreteState> lastReached = new LinkedHashSet<>(states);
        while (!lastReached.isEmpty()) {
            ConcreteState c = lastReached.iterator().next();
            lastReached.remove(c);
            for (Event e : Machine.getEvents().values()) {
                while (Z3.checkSAT(new And(
                        Machine.getInvariant(),
                        Machine.getInvariant().prime(),
                        c,
                        e.getSubstitution().getPrd(Machine.getAssignables()),
                        new Not(new Or(transitions.stream().filter(aTransition -> aTransition.getSource().equals(c) && aTransition.getLabel().equals(e)).map(aTransition -> aTransition.getTarget().prime()).toArray(ABoolExpr[]::new)))
                )) == Status.SATISFIABLE) {
                    Model model = Z3.getModel(Machine.getAssignables().stream().map(assignable -> (AAssignable) assignable.prime()).collect(Collectors.toCollection(LinkedHashSet::new)));
                    ConcreteState c_ = concreteState(states, model);
                    states.add(c_);
                    lastReached.add(c_);
                    transitions.add(new ConcreteTransition(c, e, c_));
                }
            }
        }
        return new FSM<>(initialStates, states, transitions);
    }

    private ConcreteState concreteState(LinkedHashSet<ConcreteState> states, Model model) {
        TreeMap<AAssignable, AValue> unprimedModel = new TreeMap<>();
        model.keySet().forEach(assignable -> unprimedModel.put((AAssignable) assignable.unprime(), model.get(assignable)));
        return states.stream().filter(concreteState -> concreteState.getMapping().equals(unprimedModel)).findFirst().orElse(new ConcreteState("c_" + states.size(), unprimedModel));
    }

}

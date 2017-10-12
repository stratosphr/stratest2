package algs;

import algs.heuristics.*;
import algs.outputs.ATS;
import algs.outputs.CTS;
import algs.outputs.MTS;
import com.microsoft.z3.Status;
import graphs.AbstractState;
import graphs.AbstractTransition;
import graphs.ConcreteState;
import graphs.ConcreteTransition;
import langs.eventb.Event;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.And;
import langs.eventb.exprs.bool.Or;
import solvers.z3.Model;
import solvers.z3.Z3;
import utilities.sets.Tuple2;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import static algs.heuristics.EConcreteStateColor.BLUE;
import static algs.heuristics.EConcreteStateColor.GREEN;
import static com.microsoft.z3.Status.SATISFIABLE;

/**
 * Created by gvoiron on 28/06/17.
 * Time : 11:41
 */
public final class CXPComputer extends AComputer<ATS> {

    private final LinkedHashSet<AbstractState> abstractStates;
    private final IAbstractStatesOrderingFunction abstractStatesOrderingFunction;
    private final IEventsOrderingFunction eventsOrderingFunction;
    private final LinkedHashSet<AbstractState> rq;
    private final LinkedHashSet<AbstractState> q0;
    private final LinkedHashSet<AbstractState> Q;
    private final LinkedHashSet<AbstractTransition> delta;
    private final LinkedHashSet<AbstractTransition> deltaMinus;
    private final LinkedHashSet<AbstractTransition> deltaPlus;
    private final LinkedHashSet<ConcreteState> c0;
    private final LinkedHashSet<ConcreteState> C;
    private final LinkedHashMap<ConcreteState, AbstractState> alpha;
    private final LinkedHashMap<ConcreteState, EConcreteStateColor> kappa;
    private final LinkedHashSet<ConcreteTransition> deltaC;
    private int nbStates;

    public CXPComputer(LinkedHashSet<AbstractState> abstractStates) {
        this(abstractStates, new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction());
    }

    public CXPComputer(LinkedHashSet<AbstractState> abstractStates, IAbstractStatesOrderingFunction abstractStatesOrderingFunction) {
        this(abstractStates, abstractStatesOrderingFunction, new DefaultEventsOrderingFunction());
    }

    public CXPComputer(LinkedHashSet<AbstractState> abstractStates, IEventsOrderingFunction eventsOrderingFunction) {
        this(abstractStates, new DefaultAbstractStatesOrderingFunction(), eventsOrderingFunction);
    }

    public CXPComputer(LinkedHashSet<AbstractState> abstractStates, IAbstractStatesOrderingFunction abstractStatesOrderingFunction, IEventsOrderingFunction eventsOrderingFunction) {
        this.abstractStates = abstractStates;
        this.abstractStatesOrderingFunction = abstractStatesOrderingFunction;
        this.eventsOrderingFunction = eventsOrderingFunction;
        this.rq = new LinkedHashSet<>();
        this.q0 = new LinkedHashSet<>();
        this.Q = new LinkedHashSet<>();
        this.delta = new LinkedHashSet<>();
        this.deltaMinus = new LinkedHashSet<>();
        this.deltaPlus = new LinkedHashSet<>();
        this.c0 = new LinkedHashSet<>();
        this.C = new LinkedHashSet<>();
        this.alpha = new LinkedHashMap<>();
        this.kappa = new LinkedHashMap<>();
        this.deltaC = new LinkedHashSet<>();
        this.nbStates = 0;
    }

    @Override
    protected ATS run() {
        step1();
        step2();
        MTS mts = new MTS(q0, Q, delta, deltaMinus, deltaPlus);
        CTS cts = new CTS(c0, C, alpha, kappa, deltaC);
        return new ATS(mts, cts);
    }

    private void step1() {
        for (AbstractState q : abstractStates) {
            Status status = Z3.checkSAT(new And(Machine.getInvariant(), Machine.getInvariant().prime(), Machine.getInitialisation().getPrd(Machine.getAssignables()), q.prime()));
            if (status == SATISFIABLE) {
                ConcreteState c = concreteState(q, Z3.getModel(Machine.getAssignables().stream().map(assignable -> (AAssignable) assignable.prime()).collect(Collectors.toCollection(LinkedHashSet::new))));
                q0.add(q);
                c0.add(c);
                alpha.put(c, q);
                kappa.put(c, GREEN);
            }
        }
        if (c0.isEmpty()) {
            throw new Error("No initial concrete state found for system \"" + Machine.getName() + "\".");
        }
        C.addAll(c0);
    }

    private void step2() {
        rq.addAll(q0);
        LinkedHashSet<Event> events = eventsOrderingFunction.apply(new LinkedHashSet<>(Machine.getEvents().values()));
        while (!rq.isEmpty()) {
            AbstractState q = rq.iterator().next();
            rq.remove(q);
            Q.add(q);
            for (AbstractState q_ : abstractStatesOrderingFunction.apply(new Tuple2<>(q, abstractStates))) {
                for (Event e : events) {
                    Status status = Z3.checkSAT(new And(Machine.getInvariant(), Machine.getInvariant().prime(), q, e.getSubstitution().getPrd(Machine.getAssignables()), q_.prime()));
                    if (status == SATISFIABLE) {
                        System.out.println("#" + (3 * abstractStates.size() * events.size() * abstractStates.size() - deltaC.size()));
                        Model model = Z3.getModel(Machine.getAssignables());
                        Model model_ = Z3.getModel(Machine.getAssignables().stream().map(assignable -> (AAssignable) assignable.prime()).collect(Collectors.toCollection(LinkedHashSet::new)));
                        AbstractTransition abstractTransition = new AbstractTransition(q, e, q_);
                        delta.add(abstractTransition);
                        //registerMustMinus(abstractTransition);
                        //registerMustPlus(abstractTransition);
                        if (instantiateFromGreenToAny(abstractTransition)) {
                            instantiateFromGreenToBlue(abstractTransition);
                        }
                        instantiateFromWitnesses(abstractTransition, model, model_);
                        if (!Q.contains(q_)) {
                            rq.add(q_);
                        }
                    }
                }
            }
        }
    }

    private ConcreteState concreteState(AbstractState q, Model mapping) {
        return C.stream().filter(concreteState -> concreteState.getMapping().equals(mapping)).findFirst().orElseGet(() -> new ConcreteState("c" + nbStates++ + "_" + q.getName(), mapping));
    }

    public void instantiateFromGreenToBlue(AbstractTransition abstractTransition) {
        LinkedHashSet<ConcreteState> GCS = C.stream().filter(concreteState -> kappa.get(concreteState).equals(GREEN) && alpha.get(concreteState).equals(abstractTransition.getSource())).collect(Collectors.toCollection(LinkedHashSet::new));
        LinkedHashSet<ConcreteState> BCS = C.stream().filter(concreteState -> kappa.get(concreteState).equals(BLUE) && alpha.get(concreteState).equals(abstractTransition.getTarget())).collect(Collectors.toCollection(LinkedHashSet::new));
        if (!GCS.isEmpty() && !BCS.isEmpty()) {
            Status status = Z3.checkSAT(new And(Machine.getInvariant(), Machine.getInvariant().prime(), new Or(GCS.toArray(new ABoolExpr[0])), abstractTransition.getEvent().getSubstitution().getPrd(Machine.getAssignables()), new Or(BCS.toArray(new ABoolExpr[0])).prime()));
            if (status == SATISFIABLE) {
                Model model = Z3.getModel(Machine.getAssignables());
                Model model_ = Z3.getModel(Machine.getAssignables().stream().map(assignable -> (AAssignable) assignable.prime()).collect(Collectors.toCollection(LinkedHashSet::new)));
                ConcreteState c = concreteState(abstractTransition.getSource(), model);
                ConcreteState c_ = concreteState(abstractTransition.getTarget(), model_);
                deltaC.add(new ConcreteTransition(c, abstractTransition.getEvent(), c_));
                kappa.put(c_, GREEN);
            }
        }
    }

    private boolean instantiateFromGreenToAny(AbstractTransition abstractTransition) {
        LinkedHashSet<ConcreteState> GCS = C.stream().filter(concreteState -> kappa.get(concreteState).equals(GREEN) && alpha.get(concreteState).equals(abstractTransition.getSource())).collect(Collectors.toCollection(LinkedHashSet::new));
        if (!GCS.isEmpty()) {
            Status status = Z3.checkSAT(new And(Machine.getInvariant(), Machine.getInvariant().prime(), new Or(GCS.toArray(new ABoolExpr[0])), abstractTransition.getEvent().getSubstitution().getPrd(Machine.getAssignables()), abstractTransition.getTarget().prime()));
            if (status == SATISFIABLE) {
                Model model = Z3.getModel(Machine.getAssignables());
                Model model_ = Z3.getModel(Machine.getAssignables().stream().map(assignable -> (AAssignable) assignable.prime()).collect(Collectors.toCollection(LinkedHashSet::new)));
                ConcreteState c = concreteState(abstractTransition.getSource(), model);
                ConcreteState c_ = concreteState(abstractTransition.getTarget(), model_);
                C.add(c_);
                deltaC.add(new ConcreteTransition(c, abstractTransition.getEvent(), c_));
                alpha.put(c_, abstractTransition.getTarget());
                kappa.put(c_, GREEN);
                return true;
            }
        }
        return false;
    }

    private void instantiateFromWitnesses(AbstractTransition abstractTransition, Model model, Model model_) {
        ConcreteState c = concreteState(abstractTransition.getSource(), model);
        ConcreteState c_ = concreteState(abstractTransition.getTarget(), model_);
        C.addAll(Arrays.asList(c, c_));
        alpha.put(c, abstractTransition.getSource());
        alpha.put(c_, abstractTransition.getTarget());
        if (!kappa.containsKey(c)) {
            kappa.put(c, BLUE);
        }
        if (!kappa.containsKey(c_) || kappa.get(c) == GREEN) {
            kappa.put(c_, kappa.get(c));
        }
        deltaC.add(new ConcreteTransition(c, abstractTransition.getEvent(), c_));
    }

    /*private void registerMustMinus(AbstractTransition abstractTransition) {
        Status status = Z3.checkSAT(new And(Machine.getInvariant().prime(), new Not(new Exists(new And(Machine.getInvariant(), abstractTransition.getEvent().getSubstitution().getPrd(Machine.getAssignables()), abstractTransition.getSource()), Machine.getVariables().toArray(new IntVar[Machine.getVariables().size()]))), abstractTransition.getTarget().getExpression().prime()));
        if (status == UNSATISFIABLE) {
            deltaMinus.add(abstractTransition);
        }
    }

    private void registerMustPlus(AbstractTransition abstractTransition) {
        Status status = Z3.checkSAT(new And(Machine.getInvariant(), abstractTransition.getSource(), new Not(new Exists(new And(Machine.getInvariant().prime(), abstractTransition.getEvent().getSubstitution().getPrd(Machine.getAssignables()), abstractTransition.getTarget().prime()), Machine.getVariables().stream().map(AExpr::prime).toArray(IntVar[]::new)))));
        if (status == UNSATISFIABLE) {
            deltaPlus.add(abstractTransition);
        }
    }*/

}

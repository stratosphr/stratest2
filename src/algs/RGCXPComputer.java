package algs;

import algs.heuristics.DefaultAbstractStatesOrderingFunction;
import algs.heuristics.DefaultEventsOrderingFunction;
import algs.heuristics.IAbstractStatesOrderingFunction;
import algs.heuristics.IEventsOrderingFunction;
import algs.heuristics.relevance.AAtomicPredicate;
import algs.heuristics.relevance.RelevancePredicate;
import algs.outputs.ATS;
import com.microsoft.z3.Status;
import graphs.*;
import langs.eventb.Event;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.And;
import langs.eventb.exprs.bool.Equals;
import langs.eventb.exprs.bool.GEQ;
import solvers.z3.Model;
import solvers.z3.Z3;
import utilities.sets.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

import static algs.heuristics.EConcreteStateColor.GREEN;
import static com.microsoft.z3.Status.SATISFIABLE;

/**
 * Created by gvoiron on 29/06/17.
 * Time : 21:43
 */
public final class RGCXPComputer extends AComputer<ATS> {

    private final ATS ats;
    private final RelevancePredicate relevancePredicate;
    private final IAbstractStatesOrderingFunction abstractStatesOrderingFunction;
    private final IEventsOrderingFunction eventsOrderingFunction;
    private final LinkedHashMap<ConcreteState, LinkedHashMap<AAtomicPredicate, AValue>> variantsMapping;
    private int limit;
    private int nbStates;

    @SuppressWarnings("unused")
    public RGCXPComputer(ATS ats, RelevancePredicate relevancePredicate, int limit) {
        this(ats, new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(), relevancePredicate, limit);
    }

    @SuppressWarnings("unused")
    public RGCXPComputer(ATS ats, IAbstractStatesOrderingFunction abstractStatesOrderingFunction, RelevancePredicate relevancePredicate, int limit) {
        this(ats, abstractStatesOrderingFunction, new DefaultEventsOrderingFunction(), relevancePredicate, limit);
    }

    @SuppressWarnings("unused")
    public RGCXPComputer(ATS ats, IEventsOrderingFunction eventsOrderingFunction, RelevancePredicate relevancePredicate, int limit) {
        this(ats, new DefaultAbstractStatesOrderingFunction(), eventsOrderingFunction, relevancePredicate, limit);
    }

    public RGCXPComputer(ATS ats, IAbstractStatesOrderingFunction abstractStatesOrderingFunction, IEventsOrderingFunction eventsOrderingFunction, RelevancePredicate relevancePredicate, int limit) {
        this.ats = ats.clone();
        this.abstractStatesOrderingFunction = abstractStatesOrderingFunction;
        this.eventsOrderingFunction = eventsOrderingFunction;
        this.relevancePredicate = relevancePredicate;
        this.variantsMapping = new LinkedHashMap<>();
        this.limit = limit;
        this.nbStates = ats.getCTS().getC().size();
    }

    /*@Override
    protected ATS run() {
        Tuple2<LinkedHashSet<ConcreteState>, ArrayList<ATransition<ConcreteState, Event>>> rchblPart = new ReachablePartComputer<>(ats.getCTS()).compute();
        LinkedHashSet<ConcreteState> RCS = new LinkedHashSet<>();
        Var variant = new Var("_variant");
        for (ConcreteState c : rchblPart.getFirst()) {
            if (Z3.checkSAT(new And(Machine.getInvariant(), c, new Equals(variant, relevancePredicate.getVariantC0()), new GEQ(relevancePredicate.getVariantC0(), new Int(0)))) == SATISFIABLE) {
                RCS.add(c);
                variantsMapping.put(c, new LinkedHashMap<>());
                relevancePredicate.getAtomicPredicates().forEach(atomicPredicate -> {
                    Z3.checkSAT(new And(Machine.getInvariant(), c, new Equals(variant, atomicPredicate.getVariantC0())));
                    variantsMapping.get(c).put(atomicPredicate, Z3.getModel(new LinkedHashSet<>(Collections.singletonList(variant))).get(variant));
                });
            }
        }
        Status status;
        LinkedHashSet<Event> events = eventsOrderingFunction.apply(new LinkedHashSet<>(Machine.getEvents().values()));
        while (!RCS.isEmpty()) {
            ConcreteState c = RCS.iterator().next();
            RCS.remove(c);
            AbstractState q = ats.getCTS().getAlpha().get(c);
            for (AbstractState q_ : abstractStatesOrderingFunction.apply(new Tuple2<>(q, ats.getMTS().getQ()))) {
                for (Event e : events) {
                    if (ats.getMTS().getDelta().contains(new AbstractTransition(q, e, q_))) {
                        status = Z3.checkSAT(new And(
                                Machine.getInvariant(),
                                Machine.getInvariant().prime(),
                                c,
                                e.getSubstitution().getPrd(Machine.getAssignables()),
                                q_.prime(),
                                relevancePredicate
                        ));
                        if (status == SATISFIABLE) {
                            Model model = Z3.getModel(Machine.getAssignables());
                            Model model_ = Z3.getModel(Machine.getAssignables().stream().map(assignable -> (AAssignable) assignable.prime()).collect(Collectors.toCollection(LinkedHashSet::new)));
                            ConcreteState c_ = concreteState(q_, model_);
                            if ((!ats.getCTS().getC().contains(c_) || !ats.getCTS().getKappa().get(c_).equals(GREEN))) {
                                System.err.println(relevancePredicate.getVariantC_(c, c_, variantsMapping));
                                if (Z3.checkSAT(new And(Machine.getInvariant(), Machine.getInvariant().prime(), c, c_.prime(), new Equals(variant, relevancePredicate.getVariantC_(c, c_, variantsMapping)), new GEQ(variant, new Int(0)))) == SATISFIABLE) {
                                    System.out.println(e.getName());
                                    System.out.println(c.getName() + " : " + relevancePredicate.getVariantC_(c, c_, variantsMapping));
                                    LinkedHashMap<AAtomicPredicate, AValue> mapping = new LinkedHashMap<>();
                                    Iterator<AAtomicPredicate> atomicPredicateIterator = relevancePredicate.getAtomicPredicates().iterator();
                                    Iterator<AArithExpr> operandIterator = relevancePredicate.getVariantC_(c, c_, variantsMapping).getOperands().iterator();
                                    while (atomicPredicateIterator.hasNext()) {
                                        AAtomicPredicate atomicPredicate = atomicPredicateIterator.next();
                                        AArithExpr operand = operandIterator.next();
                                        Z3.checkSAT(new Equals(variant, operand));
                                        mapping.put(atomicPredicate, Z3.getModel(new LinkedHashSet<>(Collections.singletonList(variant))).get(variant));
                                    }
                                    System.out.println(mapping.values());
                                    variantsMapping.put(c_, mapping);
                                    LinkedHashSet<ConcreteState> tmpRCS = new LinkedHashSet<>(RCS);
                                    RCS = new LinkedHashSet<>();
                                    RCS.add(c_);
                                    RCS.addAll(tmpRCS);
                                }
                            }
                            ats.getCTS().getC().add(c_);
                            ats.getCTS().getAlpha().put(c_, q_);
                            ats.getCTS().getKappa().put(c_, GREEN);
                            ats.getCTS().getC().add(c_);
                            ats.getCTS().getDeltaC().add(new ConcreteTransition(c, e, c_));
                        } else {
                            status = Z3.checkSAT(new And(
                                    Machine.getInvariant(),
                                    Machine.getInvariant().prime(),
                                    c,
                                    e.getSubstitution().getPrd(Machine.getAssignables()),
                                    q_.prime()
                            ));
                            if (status == SATISFIABLE) {
                                Model model = Z3.getModel(Machine.getAssignables());
                                Model model_ = Z3.getModel(Machine.getAssignables().stream().map(assignable -> (AAssignable) assignable.prime()).collect(Collectors.toCollection(LinkedHashSet::new)));
                                ConcreteState c_ = concreteState(q_, model_);
                                ats.getCTS().getC().add(c_);
                                ats.getCTS().getAlpha().put(c_, q_);
                                ats.getCTS().getKappa().put(c_, GREEN);
                                ats.getCTS().getC().add(c_);
                                ats.getCTS().getDeltaC().add(new ConcreteTransition(c, e, c_));
                            }
                        }
                        if (status == SATISFIABLE && --limit <= 0) {
                            return ats;
                        }
                    }
                }
            }
        }
        return ats;
    }*/

    @Override
    protected ATS run() {
        Tuple2<LinkedHashSet<ConcreteState>, ArrayList<ATransition<ConcreteState, Event>>> rchblPart = new ReachablePartComputer<>(ats.getCTS()).compute().getResult();
        LinkedHashSet<ConcreteState> RCS = new LinkedHashSet<>();
        Var variant = new Var("_variant");
        for (ConcreteState c : rchblPart.getFirst()) {
            if (Z3.checkSAT(new And(Machine.getInvariant(), c, new Equals(variant, relevancePredicate.getVariantC0(c)), new GEQ(relevancePredicate.getVariantC0(c), new Int(0)))) == SATISFIABLE) {
                AValue aValue = Z3.getModel(new LinkedHashSet<>(Collections.singletonList(variant))).get(variant);
                c.setMarker("V", aValue + " (CXP)");
                //System.out.println("# " + aValue);
                variantsMapping.put(c, new LinkedHashMap<>());
                for (AAtomicPredicate ap : relevancePredicate.getAtomicPredicates()) {
                    Z3.checkSAT(new And(Machine.getInvariant(), c, new Equals(variant, ap.getVariantC0(c))));
                    variantsMapping.get(c).put(ap, Z3.getModel(new LinkedHashSet<>(Collections.singletonList(variant))).get(variant));
                    /*if (ap instanceof AtomicPredicateMultiImplies) {
                        ((AtomicPredicateMultiImplies) ap).getImplies().forEach(imply -> {
                            Z3.checkSAT(new And(Machine.getInvariant(), c, new Equals(variant, imply.getThenPart().getVariantC0(c))));
                            variantsMapping.get(c).put(imply.getThenPart(), Z3.getModel(new LinkedHashSet<>(Collections.singletonList(variant))).get(variant));
                        });
                    }*/
                }
                RCS.add(c);
            } else {
                throw new Error("A reached concrete state does not satisfy the relevance predicate. This is probably not a problem and this error should be deleted.");
            }
        }
        /*variantsMapping.forEach((concreteState, mapping) -> {
            System.out.println(concreteState.getName() + " : ");
            mapping.forEach((ap, value) -> {
                System.out.println(TAB + ap.toString().replaceAll(NL, " ").replaceAll(TAB, "") + " = " + value);
            });
        });*/
        LinkedHashSet<ConcreteState> TRCS = new LinkedHashSet<>(RCS);
        Status status;
        LinkedHashSet<Event> events = eventsOrderingFunction.apply(new LinkedHashSet<>(Machine.getEvents().values()));
        while (!RCS.isEmpty()) {
            ConcreteState c = RCS.iterator().next();
            RCS.remove(c);
            TRCS.add(c);
            if (variantsMapping.get(c) == null) {
                throw new Error(c.toString());
            }
            AbstractState q = ats.getCTS().getAlpha().get(c);
            System.out.println(RCS.size());
            for (AbstractState q_ : abstractStatesOrderingFunction.apply(new Tuple2<>(q, ats.getMTS().getQ()))) {
                for (Event e : events) {
                    if (ats.getMTS().getDelta().contains(new AbstractTransition(q, e, q_))) {
                        status = Z3.checkSAT(new And(
                                Machine.getInvariant(),
                                Machine.getInvariant().prime(),
                                c,
                                e.getSubstitution().getPrd(Machine.getAssignables()),
                                q_.prime(),
                                relevancePredicate
                        ));
                        if (status == SATISFIABLE) {
                            ConcreteState c_ = concreteState(q_, Z3.getModel(Machine.getAssignables().stream().map(assignable -> (AAssignable) assignable.prime()).collect(Collectors.toCollection(LinkedHashSet::new))));
                            //if (!ats.getCTS().getC().contains(c_) || !ats.getCTS().getKappa().get(c_).equals(GREEN)) {
                            if (!TRCS.contains(c_)) {
                                //System.out.println(c + " " + e.getName() + " " + c_);
                                if (Z3.checkSAT(new And(
                                        Machine.getInvariant(),
                                        Machine.getInvariant().prime(),
                                        c,
                                        c_.prime(),
                                        new Equals(variant, relevancePredicate.getVariantC_(c, c_, variantsMapping)),
                                        new GEQ(variant, new Int(0))
                                )) == SATISFIABLE) {
                                    /*if (c.getName().startsWith("c21")) {
                                        throw new Error(2 + " " + c.getName() + " " + e.getName() + " " + c_.getName() + " " + c_.getMarkers());
                                    }*/
                                    AValue aValue = Z3.getModel(new LinkedHashSet<>(Collections.singletonList(variant))).get(variant);
                                    c_.setMarker("V", aValue);
                                    //System.out.println(c.getName() + " " + e.getName() + " " + c_.getName());
                                    //System.out.println("# " + aValue);
                                    variantsMapping.put(c_, new LinkedHashMap<>());
                                    Iterator<AAtomicPredicate> apIterator = relevancePredicate.getAtomicPredicates().iterator();
                                    Iterator<AArithExpr> opIterator = relevancePredicate.getVariantC_(c, c_, variantsMapping).getOperands().iterator();
                                    while (apIterator.hasNext()) {
                                        AAtomicPredicate ap = apIterator.next();
                                        AArithExpr op = opIterator.next();
                                        Z3.checkSAT(new And(Machine.getInvariant(), Machine.getInvariant().prime(), c, c_.prime(), new Equals(variant, op)));
                                        variantsMapping.get(c_).put(ap, Z3.getModel(new LinkedHashSet<>(Collections.singletonList(variant))).get(variant));
                                        /*if (ap instanceof AtomicPredicateMultiImplies) {
                                            ((AtomicPredicateMultiImplies) ap).getImplies().forEach(imply -> {
                                                Z3.checkSAT(new And(Machine.getInvariant(), c_, new Equals(variant, imply.getThenPart().getVariantC_(c, c_, variantsMapping))));
                                                variantsMapping.get(c_).put(imply.getThenPart(), Z3.getModel(new LinkedHashSet<>(Collections.singletonList(variant))).get(variant));
                                            });
                                        }*/
                                    }
                                    /*System.out.println(c_.getName() + " : ");
                                    variantsMapping.get(c_).forEach((ap, value) -> {
                                        System.out.println(TAB + ap.toString().replaceAll(NL, " ").replaceAll(TAB, "") + " = " + value);
                                    });*/
                                    LinkedHashSet<ConcreteState> tmpRCS = new LinkedHashSet<>(RCS);
                                    RCS.clear();
                                    RCS.add(c_);
                                    RCS.addAll(tmpRCS);
                                }
                            } else {
                                if (c.getName().startsWith("c46") && c_.getName().startsWith("c58")) {
                                    System.out.println(ats.getCTS().getC().contains(c_));
                                    System.out.println(ats.getCTS().getKappa().get(c_));
                                    System.out.println(c);
                                    System.out.println(e.getName());
                                    System.out.println(c_);
                                    System.out.println(relevancePredicate.getVariantC_(c, c_, variantsMapping));
                                }
                            }
                            ats.getCTS().getC().add(c_);
                            ats.getCTS().getAlpha().put(c_, q_);
                            ats.getCTS().getKappa().put(c_, GREEN);
                            ats.getCTS().getDeltaC().add(new ConcreteTransition(c, e, c_));
                        } else {
                            status = Z3.checkSAT(new And(
                                    Machine.getInvariant(),
                                    Machine.getInvariant().prime(),
                                    c,
                                    e.getSubstitution().getPrd(Machine.getAssignables()),
                                    q_.prime()
                            ));
                            if (status == SATISFIABLE) {
                                Model model_ = Z3.getModel(Machine.getAssignables().stream().map(assignable -> (AAssignable) assignable.prime()).collect(Collectors.toCollection(LinkedHashSet::new)));
                                ConcreteState c_ = concreteState(q_, model_);
                                ats.getCTS().getC().add(c_);
                                ats.getCTS().getAlpha().put(c_, q_);
                                ats.getCTS().getKappa().put(c_, GREEN);
                                ats.getCTS().getDeltaC().add(new ConcreteTransition(c, e, c_));
                            }
                        }
                    }
                }
            }
        }
        return ats;
    }

    private ConcreteState concreteState(AbstractState q, TreeMap<AAssignable, AValue> mapping) {
        return ats.getCTS().getC().stream().filter(concreteState -> concreteState.getMapping().equals(mapping)).findFirst().orElseGet(() -> new ConcreteState("c" + nbStates++ + "_" + q.getName(), mapping));
    }

}

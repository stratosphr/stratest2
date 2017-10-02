package algs.statistics;

import algs.AComputer;
import algs.ReachablePartComputer;
import algs.outputs.ATS;
import graphs.*;
import langs.eventb.Event;
import langs.eventb.Machine;
import langs.eventb.exprs.bool.Predicate;
import utilities.sets.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import static algs.statistics.EStats.*;

/**
 * Created by gvoiron on 30/06/17.
 * Time : 18:12
 */
public final class StatisticsComputer extends AComputer<LinkedHashMap<EStats, Object>> {

    private final ATS ats;
    private final LinkedHashSet<Predicate> ap;
    private final LinkedHashSet<EStats> keys;

    public StatisticsComputer(ATS ats, LinkedHashSet<Predicate> ap, EStats... keys) {
        this.ats = ats;
        this.ap = ap;
        this.keys = new LinkedHashSet<>(Arrays.asList(keys));
    }

    @Override
    protected LinkedHashMap<EStats, Object> run() {
        LinkedHashMap<EStats, Object> statistics = new LinkedHashMap<>();
        keys.forEach(key -> statistics.put(key, getStatistic(key)));
        return statistics;
    }

    @SuppressWarnings("unchecked")
    private Object getStatistic(EStats key) {
        Tuple2<LinkedHashSet<ConcreteState>, ArrayList<ATransition<ConcreteState, Event>>> reachedPart = new ReachablePartComputer<>(ats.getCTS()).compute().getResult();
        LinkedHashSet<ConcreteTransition> rchdConcreteTransitions = reachedPart.getSecond().stream().map(transition -> new ConcreteTransition(transition.getSource(), transition.getLabel(), transition.getTarget())).collect(Collectors.toCollection(LinkedHashSet::new));
        /*CTS connectedCTS = new ConnectedCTSComputer(new CTS(ats.getCTS().getC0(), reachedPart.getLeft(), ats.getCTS().getAlpha(), ats.getCTS().getKappa(), reachedPart.getRight().stream().map(edge -> new ConcreteTransition(edge.getSource(), ((LabeledArrow<ConcreteState, Event>) edge).getLabel(), edge.getTarget())).collect(Collectors.toCollection(LinkedHashSet::new)))).compute().getResult();
        List<List<ConcreteTransition>> tests = new ChinesePostmanPathsComputer(connectedCTS.getC0().iterator().next(), connectedCTS.getC(), connectedCTS.getDeltaC()).compute().getResult();
        for (int i = 0; i < tests.size(); i++) {
            for (int j = 0; j < tests.get(i).size(); j++) {
                if (tests.get(i).get(j).getEvent().getName().equals("_beta_") || tests.get(i).get(j).getEvent().getName().equals("_reset_")) {
                    tests.get(i).remove(j);
                }
            }
        }*/
        switch (key) {
            case NB_EV:
                return Machine.getEvents().size();
            case NB_AP:
                return ap.size();
            case NB_AS:
                return ats.getMTS().getQ().size();
            /*case NB_TESTS:
                return tests.size();
            case NB_TEST_STEPS:
                return tests.stream().map(List::size).mapToInt(integer -> integer).sum();*/
            case NB_RCHD_AS:
                return ((LinkedHashSet<AbstractState>) getStatistic(SET_RCHD_AS)).size();
            case NB_AT:
                return ats.getMTS().getDelta().size();
            case NB_RCHD_AT:
                return ((LinkedHashSet<AbstractTransition>) getStatistic(SET_RCHD_AT)).size();//ats.getMTS().getDelta().stream().filter(abstractTransition -> rchdConcreteTransitions.stream().anyMatch(concreteTransition -> ats.getCTS().getAlpha().get(concreteTransition.getSource()).equals(abstractTransition.getSource()) && concreteTransition.getEvent().equals(abstractTransition.getEvent()) && ats.getCTS().getAlpha().get(concreteTransition.getTarget()).equals(abstractTransition.getTarget()))).count();
            case NB_CS:
                return ats.getCTS().getC().size();
            case NB_RCHD_CS:
                return reachedPart.getFirst().size();
            case NB_CT:
                return ats.getCTS().getDeltaC().size();
            case NB_RCHD_CT:
                return reachedPart.getSecond().size();
            case COV_AS:
                return ats.getMTS().getQ().isEmpty() ? 0.0 : 100.0 * ats.getMTS().getQ().stream().filter(abstractState -> reachedPart.getFirst().stream().anyMatch(concreteState -> ats.getCTS().getAlpha().get(concreteState).equals(abstractState))).count() / ats.getMTS().getQ().size();
            case COV_AT:
                return ats.getMTS().getDelta().isEmpty() ? 0.0 : 100.0 * ats.getMTS().getDelta().stream().filter(abstractTransition -> rchdConcreteTransitions.stream().anyMatch(concreteTransition -> ats.getCTS().getAlpha().get(concreteTransition.getSource()).equals(abstractTransition.getSource()) && concreteTransition.getEvent().equals(abstractTransition.getEvent()) && ats.getCTS().getAlpha().get(concreteTransition.getTarget()).equals(abstractTransition.getTarget()))).count() / ats.getMTS().getDelta().size();
            case SET_RCHD_AS:
                return ats.getMTS().getQ().stream().filter(abstractState -> reachedPart.getFirst().stream().anyMatch(concreteState -> ats.getCTS().getAlpha().get(concreteState).equals(abstractState))).collect(Collectors.toCollection(LinkedHashSet::new));
            case SET_RCHD_AT:
                return ats.getMTS().getDelta().stream().filter(abstractTransition -> rchdConcreteTransitions.stream().anyMatch(concreteTransition -> ats.getCTS().getAlpha().get(concreteTransition.getSource()).equals(abstractTransition.getSource()) && concreteTransition.getEvent().equals(abstractTransition.getEvent()) && ats.getCTS().getAlpha().get(concreteTransition.getTarget()).equals(abstractTransition.getTarget()))).collect(Collectors.toCollection(LinkedHashSet::new));
            case SET_UNRCHD_AS:
                LinkedHashSet<AbstractState> reachedAbstractStates = (LinkedHashSet<AbstractState>) getStatistic(SET_RCHD_AS);
                return ats.getMTS().getQ().stream().filter(abstractState -> !reachedAbstractStates.contains(abstractState)).collect(Collectors.toCollection(LinkedHashSet::new));
            case SET_UNRCHD_AT:
                LinkedHashSet<AbstractTransition> reachedAbstractTransitions = (LinkedHashSet<AbstractTransition>) getStatistic(SET_RCHD_AT);
                return ats.getMTS().getDelta().stream().filter(abstractTransition -> !reachedAbstractTransitions.contains(abstractTransition)).collect(Collectors.toCollection(LinkedHashSet::new));
            case RHO_CS:
                return 1.0 * (Integer) getStatistic(NB_CS) / (Integer) getStatistic(NB_RCHD_AS);
            case RHO_CT:
                return 1.0 * (Integer) getStatistic(NB_CT) / (Integer) getStatistic(NB_RCHD_AT);
            /*case TESTS:
                return tests;*/
            default:
                throw new Error("Unhandled statistics key \"" + key + "\".");
        }
    }

}

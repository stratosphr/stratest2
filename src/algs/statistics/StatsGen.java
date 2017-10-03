package algs.statistics;

import algs.ReachablePartComputer;
import algs.outputs.ATS;
import graphs.ATransition;
import graphs.AbstractState;
import graphs.AbstractTransition;
import graphs.ConcreteState;
import langs.eventb.Event;
import langs.eventb.Machine;
import langs.eventb.exprs.bool.Predicate;
import utilities.sets.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 02/10/17.
 * Time : 11:51
 */
public final class StatsGen {

    private final ATS ats;
    private final LinkedHashSet<Predicate> ap;
    private final LinkedHashSet<EStats> desiredStatistics;
    private Tuple2<LinkedHashSet<ConcreteState>, ArrayList<ATransition<ConcreteState, Event>>> rchdPart;
    private LinkedHashSet<AbstractState> rchdAS;
    private LinkedHashSet<AbstractTransition> rchdAT;
    private Object desiredStat;

    public StatsGen(ATS ats, LinkedHashSet<Predicate> ap) {
        this(ats, ap, EStats.values());
    }

    public StatsGen(ATS ats, LinkedHashSet<Predicate> ap, EStats... desiredStats) {
        this.ats = ats;
        this.ap = ap;
        this.desiredStatistics = new LinkedHashSet<>(Arrays.asList(desiredStats));
    }

    public Object getDesiredStat(EStats stat) {
        switch (stat) {
            case NB_EV:
                return getNbEv();
            case NB_AP:
                return getNbAP();
            case NB_AS:
                return getNbAS();
            case NB_AT:
                return getNbAT();
            case NB_RCHD_AS:
                return getNbRchdAS();
            case COV_AS:
                return getCovAS();
            case NB_RCHD_AT:
                return getNbRchdAT();
            case COV_AT:
                return getCovAT();
            case NB_CS:
                return getNbCS();
            case NB_RCHD_CS:
                return getNbRchdCS();
            case NB_CT:
                return getNbCT();
            case NB_RCHD_CT:
                return getNbRchdCT();
            case RHO_CS:
                return getRhoCS();
            case RHO_CT:
                return getRhoCT();
            case SET_RCHD_AS:
                return getSetRchdAS();
            case SET_RCHD_AT:
                return getSetRchdAT();
            case SET_UNRCHD_AS:
                return getSetUnrchdAS();
            case SET_UNRCHD_AT:
                return getSetUnrchdAT();
            default:
                throw new Error("Trying to access statistics for unknown EStat value.");
        }
    }

    public LinkedHashMap<EStats, Object> getAllDesiredStats() {
        LinkedHashMap<EStats, Object> allDesiredStats = new LinkedHashMap<>();
        desiredStatistics.forEach(stat -> allDesiredStats.put(stat, getDesiredStat(stat)));
        return allDesiredStats;
    }

    public Integer getNbEv() {
        return Machine.getEvents().size();
    }

    public Integer getNbAP() {
        return ap.size();
    }

    public Integer getNbAS() {
        return ats.getMTS().getQ().size();
    }

    public Integer getNbAT() {
        return ats.getMTS().getDelta().size();
    }

    public Integer getNbRchdAS() {
        return getRchdAS().size();
    }

    public double getCovAS() {
        return getNbAS() == 0 ? 0 : 100.0 * getNbRchdAS() / getNbAS();
    }

    public Integer getNbRchdAT() {
        return getRchdAT().size();
    }

    public double getCovAT() {
        return getNbAT() == 0 ? 0 : 100.0 * getNbRchdAT() / getNbAT();
    }

    public Integer getNbCS() {
        return ats.getCTS().getC().size();
    }

    public Integer getNbRchdCS() {
        return getRchdPart().getFirst().size();
    }

    public Integer getNbCT() {
        return ats.getCTS().getDeltaC().size();
    }

    public Integer getNbRchdCT() {
        return getRchdPart().getSecond().size();
    }

    public double getRhoCS() {
        return getNbRchdCS() == 0 ? 0 : 1.0 * getNbCS() / getNbRchdCS();
    }

    public double getRhoCT() {
        return getNbRchdCT() == 0 ? 0 : 1.0 * getNbCT() / getNbRchdCT();
    }

    public LinkedHashSet<AbstractState> getSetRchdAS() {
        return getRchdAS();
    }

    public LinkedHashSet<AbstractTransition> getSetRchdAT() {
        return getRchdAT();
    }

    public LinkedHashSet<AbstractState> getSetUnrchdAS() {
        return ats.getMTS().getQ().stream().filter(abstractState -> !getSetRchdAS().contains(abstractState)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public LinkedHashSet<AbstractTransition> getSetUnrchdAT() {
        return ats.getMTS().getDelta().stream().filter(abstractTransition -> !getSetRchdAT().contains(abstractTransition)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Tuple2<LinkedHashSet<ConcreteState>, ArrayList<ATransition<ConcreteState, Event>>> getRchdPart() {
        if (rchdPart == null) {
            rchdPart = new ReachablePartComputer<>(ats.getCTS()).compute().getResult();
        }
        return rchdPart;
    }

    private LinkedHashSet<AbstractState> getRchdAS() {
        if (rchdAS == null) {
            rchdAS = getRchdPart().getFirst().stream().map(concreteState -> ats.getCTS().getAlpha().get(concreteState)).collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return rchdAS;
    }

    private LinkedHashSet<AbstractTransition> getRchdAT() {
        if (rchdAT == null) {
            rchdAT = ats.getMTS().getDelta().stream().filter(abstractTransition -> getRchdPart().getSecond().stream().anyMatch(concreteTransition -> ats.getCTS().getAlpha().get(concreteTransition.getSource()).equals(abstractTransition.getSource()) && concreteTransition.getLabel().equals(abstractTransition.getLabel()) && ats.getCTS().getAlpha().get(concreteTransition.getTarget()).equals(abstractTransition.getTarget()))).collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return rchdAT;
    }

    public LinkedHashSet<EStats> getDesiredStatistics() {
        return desiredStatistics;
    }

}

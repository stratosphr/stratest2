package algs.outputs;

import algs.statistics.EStatistics;
import algs.statistics.StatisticsComputer;
import langs.eventb.exprs.bool.Predicate;
import utilities.ICloneable;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 18/06/17.
 * Time : 20:44
 */
public final class ATS implements ICloneable<ATS> {

    private final MTS mts;
    private final CTS cts;

    public ATS(MTS mts, CTS cts) {
        this.mts = mts;
        this.cts = cts;
    }

    public MTS getMTS() {
        return mts;
    }

    public CTS getCTS() {
        return cts;
    }

    public LinkedHashMap<EStatistics, Object> getStatistics(LinkedHashSet<Predicate> abstractionPredicates) {
        return new StatisticsComputer(this, abstractionPredicates, EStatistics.values()).compute().getResult();
    }

    public LinkedHashMap<EStatistics, Object> getStatistics(LinkedHashSet<Predicate> abstractionPredicates, EStatistics... keys) {
        return new StatisticsComputer(this, abstractionPredicates, keys).compute().getResult();
    }

    @Override
    public ATS clone() {
        return new ATS(mts.clone(), cts.clone());
    }

}

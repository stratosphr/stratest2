package algs.statistics;

import algs.AbstractStatesComputer;
import algs.CXPComputer;
import algs.RGCXPComputer;
import algs.heuristics.IAbstractStatesOrderingFunction;
import algs.heuristics.IEventsOrderingFunction;
import algs.heuristics.relevance.RelevancePredicate;
import algs.outputs.ATS;
import algs.outputs.ComputerResult;
import formatters.eventb.EventBFormatter;
import formatters.graphs.DefaultGVZFormatter;
import formatters.statistics.StatisticsFormatter;
import graphs.AState;
import graphs.AbstractState;
import langs.eventb.Machine;
import langs.eventb.exprs.AExpr;
import langs.eventb.exprs.bool.Predicate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import static formatters.graphs.ERankDir.LR;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static utilities.Chars.NL;

/**
 * Created by gvoiron on 12/07/17.
 * Time : 00:46
 */
public final class Saver {

    @SuppressWarnings({"SameParameterValue", "ResultOfMethodCallIgnored"})
    public static void save(String subFolderName, LinkedHashSet<Predicate> ap, IAbstractStatesOrderingFunction abstractStatesOrderingFunction, IEventsOrderingFunction eventsOrderingFunction, RelevancePredicate relevancePredicate, int limit) {
        ComputerResult<LinkedHashSet<AbstractState>> compute = new AbstractStatesComputer(ap).compute();
        String asComputationTime = compute.getComputationTime().toString();
        LinkedHashSet<AbstractState> as = compute.getResult();
        /* CXP **********************************/
        ComputerResult<ATS> cxp = new CXPComputer(as, abstractStatesOrderingFunction, eventsOrderingFunction).compute();
        String cxpComputationTime = cxp.getComputationTime().toString();
        ATS cxpATS = cxp.getResult();
        LinkedHashMap<EStatistics, Object> cxpStatistics = cxpATS.getStatistics(ap);
        /* **************************************/
        /* RGCXP ********************************/
        ComputerResult<ATS> rgcxp = new RGCXPComputer(cxpATS, abstractStatesOrderingFunction, eventsOrderingFunction, relevancePredicate, limit).compute();
        String rgcxpComputationTime = rgcxp.getComputationTime().toString();
        ATS rgcxpATS = rgcxp.getResult();
        LinkedHashMap<EStatistics, Object> rgcxpStatistics = rgcxpATS.getStatistics(ap);
        /* **************************************/
        File dir = new File(new File("resources/results", Machine.getName()), subFolderName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            Files.write(new File(dir, Machine.getName() + ".mch").toPath(), EventBFormatter.format(new Machine()).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dir, subFolderName + ".ap").toPath(), ("Abstraction Predicates (" + ap.size() + "):" + NL + NL + ap.stream().map(AExpr::toString).collect(Collectors.joining(NL))).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dir, subFolderName + ".as").toPath(), ("Abstract States (" + as.size() + " in " + asComputationTime + "):" + NL + NL + as.stream().map(AState::toString).collect(Collectors.joining(NL))).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dir, subFolderName + ".rel").toPath(), ("Relevance Predicate:" + NL + NL + relevancePredicate).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dir, subFolderName + ".row").toPath(), (
                    cxpStatistics.keySet().stream().filter(eStatistics -> !eStatistics.toString().startsWith("SET")).map(eStatistics -> cxpStatistics.get(eStatistics).toString()).collect(Collectors.joining(" ")) + NL +
                            rgcxpStatistics.keySet().stream().filter(eStatistics -> !eStatistics.toString().startsWith("SET")).map(eStatistics -> rgcxpStatistics.get(eStatistics).toString()).collect(Collectors.joining(" ")) + NL
            ).getBytes(), CREATE, TRUNCATE_EXISTING);
            /* MTS **********************************/
            Files.write(new File(dir, "mts_small.dot").toPath(), cxpATS.getMTS().accept(new DefaultGVZFormatter<>(false, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dir, "mts_full.dot").toPath(), cxpATS.getMTS().accept(new DefaultGVZFormatter<>(true, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            /* **************************************/
            /* CXP **********************************/
            Files.write(new File(dir, "cxp.stat").toPath(), ("Results for CXP (in " + cxpComputationTime + "):" + NL + NL + new StatisticsFormatter(cxpStatistics).format()).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dir, "cxp_small.dot").toPath(), cxpATS.getCTS().accept(new DefaultGVZFormatter<>(false, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dir, "cxp_full.dot").toPath(), cxpATS.getCTS().accept(new DefaultGVZFormatter<>(true, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            /* **************************************/
            /* RGCXP **********************************/
            Files.write(new File(dir, "rgcxp.stat").toPath(), ("Results for RGCXP (in " + rgcxpComputationTime + "):" + NL + NL + new StatisticsFormatter(rgcxpStatistics).format()).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dir, "rgcxp_small.dot").toPath(), rgcxpATS.getCTS().accept(new DefaultGVZFormatter<>(false, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dir, "rgcxp_full.dot").toPath(), rgcxpATS.getCTS().accept(new DefaultGVZFormatter<>(true, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            /* **************************************/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package algs.statistics;

import algs.*;
import algs.heuristics.EConcreteStateColor;
import algs.heuristics.IAbstractStatesOrderingFunction;
import algs.heuristics.IEventsOrderingFunction;
import algs.heuristics.relevance.RelevancePredicate;
import algs.outputs.ATS;
import algs.outputs.CTS;
import algs.outputs.ComputerResult;
import formatters.eventb.EventBFormatter;
import formatters.graphs.DefaultGVZFormatter;
import formatters.statistics.StatisticsFormatter;
import formatters.statistics.StatsFormatter;
import graphs.*;
import langs.eventb.Event;
import langs.eventb.Machine;
import langs.eventb.exprs.AExpr;
import langs.eventb.exprs.bool.Predicate;
import utilities.Resources;
import utilities.Time;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import static algs.heuristics.EConcreteStateColor.GREEN;
import static formatters.graphs.ERankDir.LR;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static utilities.Chars.NL;

/**
 * Created by gvoiron on 12/07/17.
 * Time : 00:46
 */
public final class Saver {

    public static void save2(String subFolderName, LinkedHashSet<Predicate> ap, IAbstractStatesOrderingFunction abstractStatesOrderingFunction, IEventsOrderingFunction eventsOrderingFunction, RelevancePredicate relevancePredicate, int limit) {
        save2(subFolderName, ap, abstractStatesOrderingFunction, eventsOrderingFunction, relevancePredicate, limit, false);
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "unchecked"})
    public static void save2(String subFolderName, LinkedHashSet<Predicate> ap, IAbstractStatesOrderingFunction abstractStatesOrderingFunction, IEventsOrderingFunction eventsOrderingFunction, RelevancePredicate relevancePredicate, int limit, boolean computeFull) {
        ComputerResult<LinkedHashSet<AbstractState>> asComputer = new AbstractStatesComputer(ap).compute();
        Time asComputationTime = asComputer.getComputationTime();
        LinkedHashSet<AbstractState> as = asComputer.getResult();
        /* CXP **********************************/
        ComputerResult<ATS> cxp = new CXPComputer(as, abstractStatesOrderingFunction).compute();
        Time cxpComputationTime = cxp.getComputationTime();
        ATS cxpATS = cxp.getResult();
        String cxpStats = StatsFormatter.format(new StatsGen(cxpATS, ap));
        /* **************************************/
        /* RGCXP **********************************/
        ComputerResult<ATS> rgcxp = new RGCXPComputer(cxpATS, relevancePredicate, limit).compute();
        Time rgcxpComputationTime = rgcxp.getComputationTime();
        ATS rgcxpATS = rgcxp.getResult();
        String rgcxpStats = StatsFormatter.format(new StatsGen(rgcxpATS, ap));
        /* **************************************/
        File dir = new File(new File(Resources.RESULTS, Machine.getName()), subFolderName);
        File statsDir = new File(dir, "stats");
        File dotDir = new File(dir, "dot");
        File pdfDir = new File(dir, "pdf");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!statsDir.exists()) {
            statsDir.mkdirs();
        }
        if (!dotDir.exists()) {
            dotDir.mkdirs();
        }
        if (!pdfDir.exists()) {
            pdfDir.mkdirs();
        }
        try {
            Files.write(new File(dir, Machine.getName() + ".mch").toPath(), EventBFormatter.format(new Machine()).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(statsDir, subFolderName + ".ap").toPath(), ("Abstraction Predicates (" + ap.size() + "):" + NL + NL + ap.stream().map(AExpr::toString).collect(Collectors.joining(NL))).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(statsDir, subFolderName + ".as").toPath(), ("Abstract States (" + as.size() + " in " + asComputationTime + "):" + NL + NL + as.stream().map(AState::toString).collect(Collectors.joining(NL))).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(statsDir, subFolderName + ".rel").toPath(), ("Relevance Predicate:" + NL + NL + relevancePredicate).getBytes(), CREATE, TRUNCATE_EXISTING);
            /* MTS **********************************/
            Files.write(new File(dotDir, "mts_small.dot").toPath(), cxpATS.getMTS().accept(new DefaultGVZFormatter<>(false, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dotDir, "mts_full.dot").toPath(), cxpATS.getMTS().accept(new DefaultGVZFormatter<>(true, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            /* **************************************/
            /* CXP **********************************/
            LinkedHashMap<EStats, Object> cxpDesiredStats = new StatsGen(cxpATS, ap, Arrays.stream(EStats.values()).filter(eStats -> !eStats.toString().startsWith("SET") && !eStats.toString().startsWith("RHO")).toArray(EStats[]::new)).getAllDesiredStats();
            Files.write(new File(statsDir, "cxp.row").toPath(), (cxpDesiredStats.keySet().stream().map(stat -> new DecimalFormat("##.##").format(cxpDesiredStats.get(stat)).replaceAll(",", ".")).collect(Collectors.joining(" ")) + " " + cxpComputationTime).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(statsDir, "cxp.stat").toPath(), ("Results for CXP (in " + cxpComputationTime + "):" + NL + NL + cxpStats).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dotDir, "cxp_small.dot").toPath(), cxpATS.getCTS().accept(new DefaultGVZFormatter<>(false, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dotDir, "cxp_full.dot").toPath(), cxpATS.getCTS().accept(new DefaultGVZFormatter<>(true, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            /* **************************************/
            /* RGCXP **********************************/
            LinkedHashMap<EStats, Object> rgcxpDesiredStats = new StatsGen(rgcxpATS, ap, Arrays.stream(EStats.values()).filter(eStats -> !eStats.toString().startsWith("SET") && !eStats.toString().startsWith("RHO")).toArray(EStats[]::new)).getAllDesiredStats();
            Files.write(new File(statsDir, "rgcxp.row").toPath(), (rgcxpDesiredStats.keySet().stream().map(stat -> new DecimalFormat("##.##").format(rgcxpDesiredStats.get(stat)).replaceAll(",", ".")).collect(Collectors.joining(" ")) + " " + rgcxpComputationTime).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(statsDir, "rgcxp.stat").toPath(), ("Results for RGCXP (in " + rgcxpComputationTime + "):" + NL + NL + rgcxpStats).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dotDir, "rgcxp_small.dot").toPath(), rgcxpATS.getCTS().accept(new DefaultGVZFormatter<>(false, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dotDir, "rgcxp_full.dot").toPath(), rgcxpATS.getCTS().accept(new DefaultGVZFormatter<>(true, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            /* **************************************/
            /* FULL **********************************/
            if (computeFull) {
                ComputerResult<FSM<ConcreteState, Event>> full = new FullFSMComputer().compute();
                FSM<ConcreteState, Event> fullFSM = full.getResult();
                Time fullComputationTime = full.getComputationTime();
                LinkedHashMap<ConcreteState, AbstractState> alpha = new LinkedHashMap<>();
                LinkedHashMap<ConcreteState, EConcreteStateColor> kappa = new LinkedHashMap<>();
                fullFSM.getStates().forEach(c -> {
                    alpha.put(c, new AbstractStateComputer(c, as).compute().getResult());
                    kappa.put(c, GREEN);
                });
                ATS fullATS = new ATS(cxpATS.getMTS(), new CTS(fullFSM.getInitialStates(), fullFSM.getStates(), alpha, kappa, (LinkedHashSet<ConcreteTransition>) fullFSM.getTransitions()));
                String fullStats = StatsFormatter.format(new StatsGen(fullATS, ap));
                LinkedHashMap<EStats, Object> fullDesiredStats = new StatsGen(fullATS, ap, Arrays.stream(EStats.values()).filter(eStats -> !eStats.toString().startsWith("SET") && !eStats.toString().startsWith("RHO")).toArray(EStats[]::new)).getAllDesiredStats();
                Files.write(new File(statsDir, "full.row").toPath(), (fullDesiredStats.keySet().stream().map(stat -> new DecimalFormat("##.##").format(fullDesiredStats.get(stat)).replaceAll(",", ".")).collect(Collectors.joining(" ")) + " " + fullComputationTime).getBytes(), CREATE, TRUNCATE_EXISTING);
                Files.write(new File(statsDir, "full.stat").toPath(), ("Results for FULL (in " + fullComputationTime + "):" + NL + NL + fullStats).getBytes(), CREATE, TRUNCATE_EXISTING);
                //Files.write(new File(dotDir, "full_small.dot").toPath(), fullATS.getCTS().accept(new DefaultGVZFormatter<>(false, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
                //Files.write(new File(dotDir, "full_full.dot").toPath(), fullATS.getCTS().accept(new DefaultGVZFormatter<>(true, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            }
            /* ***************************************/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings({"SameParameterValue", "ResultOfMethodCallIgnored"})
    public static void save(String subFolderName, LinkedHashSet<Predicate> ap, IAbstractStatesOrderingFunction abstractStatesOrderingFunction, IEventsOrderingFunction eventsOrderingFunction, RelevancePredicate relevancePredicate, int limit) {
        ComputerResult<LinkedHashSet<AbstractState>> compute = new AbstractStatesComputer(ap).compute();
        String asComputationTime = compute.getComputationTime().toString();
        LinkedHashSet<AbstractState> as = compute.getResult();
        /* CXP **********************************/
        ComputerResult<ATS> cxp = new CXPComputer(as, abstractStatesOrderingFunction, eventsOrderingFunction).compute();
        String cxpComputationTime = cxp.getComputationTime().toString();
        ATS cxpATS = cxp.getResult();
        //LinkedHashMap<EStats, Object> cxpStatistics = cxpATS.getStatistics(ap);
        /* **************************************/
        /* RGCXP ********************************/
        ComputerResult<ATS> rgcxp = new RGCXPComputer(cxpATS, abstractStatesOrderingFunction, eventsOrderingFunction, relevancePredicate, limit).compute();
        String rgcxpComputationTime = rgcxp.getComputationTime().toString();
        ATS rgcxpATS = rgcxp.getResult();
        //LinkedHashMap<EStats, Object> rgcxpStatistics = rgcxpATS.getStatistics(ap);
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
            /*Files.write(new File(dir, subFolderName + ".row").toPath(), (
                    cxpStatistics.keySet().stream().filter(eStatistics -> !eStatistics.toString().startsWith("SET")).map(eStatistics -> cxpStatistics.get(eStatistics).toString()).collect(Collectors.joining(" ")) + NL +
                            rgcxpStatistics.keySet().stream().filter(eStatistics -> !eStatistics.toString().startsWith("SET")).map(eStatistics -> rgcxpStatistics.get(eStatistics).toString()).collect(Collectors.joining(" ")) + NL
            ).getBytes(), CREATE, TRUNCATE_EXISTING);*/
            /* MTS **********************************/
            Files.write(new File(dir, "mts_small.dot").toPath(), cxpATS.getMTS().accept(new DefaultGVZFormatter<>(false, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dir, "mts_full.dot").toPath(), cxpATS.getMTS().accept(new DefaultGVZFormatter<>(true, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            /* **************************************/
            /* CXP **********************************/
            //Files.write(new File(dir, "cxp.stat").toPath(), ("Results for CXP (in " + cxpComputationTime + "):" + NL + NL + new StatisticsFormatter(cxpStatistics).format()).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dir, "cxp_small.dot").toPath(), cxpATS.getCTS().accept(new DefaultGVZFormatter<>(false, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dir, "cxp_full.dot").toPath(), cxpATS.getCTS().accept(new DefaultGVZFormatter<>(true, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            /* **************************************/
            /* RGCXP **********************************/
            //Files.write(new File(dir, "rgcxp.stat").toPath(), ("Results for RGCXP (in " + rgcxpComputationTime + "):" + NL + NL + new StatisticsFormatter(rgcxpStatistics).format()).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dir, "rgcxp_small.dot").toPath(), rgcxpATS.getCTS().accept(new DefaultGVZFormatter<>(false, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            Files.write(new File(dir, "rgcxp_full.dot").toPath(), rgcxpATS.getCTS().accept(new DefaultGVZFormatter<>(true, LR)).getBytes(), CREATE, TRUNCATE_EXISTING);
            /* **************************************/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void saveFull(String subFolderName, FSM<ConcreteState, Event> full, Time time) {
        File dir = new File(new File("resources/results", Machine.getName()), subFolderName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            /* FULL **********************************/
            Files.write(new File(dir, "full.stat").toPath(), ("Results for CXP (in " + time + "):" + NL + NL + new StatisticsFormatter(new StatisticsComputer(new ATS(null, new CTS(full.getInitialStates(), full.getStates(), null, null, full.getTransitions().stream().map(o -> ((ConcreteTransition) o)).collect(Collectors.toCollection(LinkedHashSet::new)))), new LinkedHashSet<>(), EStats.NB_CS, EStats.NB_CT).compute().getResult()).format()).getBytes(), CREATE, TRUNCATE_EXISTING);
            /* **************************************/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

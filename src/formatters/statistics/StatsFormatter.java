package formatters.statistics;

import algs.statistics.EStats;
import algs.statistics.StatsGen;
import formatters.AFormatter;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 02/10/17.
 * Time : 12:00
 */
public final class StatsFormatter extends AFormatter {

    public static String format(StatsGen statsGen) {
        StatsFormatter formatter = new StatsFormatter();
        LinkedHashSet<EStats> desiredStatistics = statsGen.getDesiredStatistics();
        StringBuilder formatted = new StringBuilder();
        for (EStats statistics : desiredStatistics) {
            switch (statistics) {
                case NB_EV:
                    formatted.append(formatter.line(statistics + " : " + statsGen.getNbEv()));
                    break;
                case NB_AP:
                    formatted.append(formatter.line(statistics + " : " + statsGen.getNbAP()));
                    break;
                case NB_AS:
                    formatted.append(formatter.line(statistics + " : " + statsGen.getNbAS()));
                    break;
                case NB_AT:
                    formatted.append(formatter.line(statistics + " : " + statsGen.getNbAT()));
                    break;
                case NB_RCHD_AS:
                    formatted.append(formatter.line(statistics + " : " + statsGen.getNbRchdAS()));
                    break;
                case COV_AS:
                    formatted.append(formatter.line(statistics + " : " + statsGen.getCovAS()));
                    break;
                case NB_RCHD_AT:
                    formatted.append(formatter.line(statistics + " : " + statsGen.getNbRchdAT()));
                    break;
                case COV_AT:
                    formatted.append(formatter.line(statistics + " : " + statsGen.getCovAT()));
                    break;
                case NB_CS:
                    formatted.append(formatter.line(statistics + " : " + statsGen.getNbCS()));
                    break;
                case NB_RCHD_CS:
                    formatted.append(formatter.line(statistics + " : " + statsGen.getNbRchdCS()));
                    break;
                case NB_CT:
                    formatted.append(formatter.line(statistics + " : " + statsGen.getNbCT()));
                    break;
                case NB_RCHD_CT:
                    formatted.append(formatter.line(statistics + " : " + statsGen.getNbRchdCT()));
                    break;
                case RHO_CS:
                    formatted.append(formatter.line(statistics + " : " + statsGen.getRhoCS()));
                    break;
                case RHO_CT:
                    formatted.append(formatter.line(statistics + " : " + statsGen.getRhoCT()));
                    break;
                case SET_RCHD_AS:
                    formatted.append(formatter.line(statistics + " : ")).append(formatter.indentRight()).append(statsGen.getSetRchdAS().stream().map(abstractState -> formatter.indentLine(abstractState.toString())).collect(Collectors.joining())).append(formatter.indentLeft());
                    break;
                case SET_RCHD_AT:
                    formatted.append(formatter.line(statistics + " : ")).append(formatter.indentRight()).append(statsGen.getSetRchdAT().stream().map(abstractState -> formatter.indentLine(abstractState.toString())).collect(Collectors.joining())).append(formatter.indentLeft());
                    break;
                case SET_UNRCHD_AS:
                    formatted.append(formatter.line(statistics + " : ")).append(formatter.indentRight()).append(statsGen.getSetUnrchdAS().stream().map(abstractState -> formatter.indentLine(abstractState.toString())).collect(Collectors.joining())).append(formatter.indentLeft());
                    break;
                case SET_UNRCHD_AT:
                    formatted.append(formatter.line(statistics + " : ")).append(formatter.indentRight()).append(statsGen.getSetUnrchdAT().stream().map(abstractState -> formatter.indentLine(abstractState.toString())).collect(Collectors.joining())).append(formatter.indentLeft());
                    break;
            }
            formatted.append(formatter.line()).append(formatter.line());
        }
        return formatted.toString();
    }

}

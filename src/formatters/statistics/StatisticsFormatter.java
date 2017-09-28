package formatters.statistics;

import algs.statistics.EStatistics;
import formatters.AFormatter;
import graphs.AbstractState;
import graphs.AbstractTransition;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Collectors;

import static utilities.Chars.NL;

/**
 * Created by gvoiron on 11/07/17.
 * Time : 23:30
 */
public final class StatisticsFormatter extends AFormatter {

    private final LinkedHashMap<EStatistics, Object> statistics;

    public StatisticsFormatter(LinkedHashMap<EStatistics, Object> statistics) {
        this.statistics = statistics;
    }

    @SuppressWarnings("unchecked")
    public String format() {
        StringBuilder formatted = new StringBuilder();
        for (EStatistics key : statistics.keySet()) {
            switch (key) {
                case NB_EV:
                    formatted.append(key).append(": ").append(statistics.get(key));
                    break;
                case NB_AP:
                    formatted.append(key).append(": ").append(statistics.get(key));
                    break;
                case NB_AS:
                    formatted.append(key).append(": ").append(statistics.get(key));
                    break;
                case NB_AT:
                    formatted.append(key).append(": ").append(statistics.get(key));
                    break;
                case NB_CS:
                    formatted.append(key).append(": ").append(statistics.get(key));
                    break;
                case NB_CT:
                    formatted.append(key).append(": ").append(statistics.get(key));
                    break;
                /*case NB_TESTS:
                    formatted.append(key).append(": ").append(statistics.get(key));
                    break;
                case NB_TEST_STEPS:
                    formatted.append(key).append(": ").append(statistics.get(key));
                    break;*/
                case NB_RCHD_AS:
                    formatted.append(key).append(": ").append(statistics.get(key));
                    break;
                case NB_RCHD_AT:
                    formatted.append(key).append(": ").append(statistics.get(key));
                    break;
                case NB_RCHD_CS:
                    formatted.append(key).append(": ").append(statistics.get(key));
                    break;
                case NB_RCHD_CT:
                    formatted.append(key).append(": ").append(statistics.get(key));
                    break;
                case COV_AS:
                    formatted.append(key).append(": ").append(statistics.get(key)).append(" %");
                    break;
                case COV_AT:
                    formatted.append(key).append(": ").append(statistics.get(key)).append(" %");
                    break;
                case RHO_CS:
                    formatted.append(key).append(": ").append(statistics.get(key));
                    break;
                case RHO_CT:
                    formatted.append(key).append(": ").append(statistics.get(key));
                    break;
                case SET_RCHD_AS:
                    indentRight();
                    formatted.append(key).append(":").append(NL).append(((Set<AbstractState>) statistics.get(key)).stream().map(abstractState -> indentLine(abstractState.toString())).collect(Collectors.joining()));
                    indentLeft();
                    break;
                case SET_RCHD_AT:
                    indentRight();
                    formatted.append(key).append(":").append(NL).append(((Set<AbstractTransition>) statistics.get(key)).stream().map(abstractTransition -> indentLine(abstractTransition.getSource().getName() + " -[ " + abstractTransition.getEvent().getName() + " ]-> " + abstractTransition.getTarget().getName())).collect(Collectors.joining()));
                    indentLeft();
                    break;
                case SET_UNRCHD_AS:
                    indentRight();
                    formatted.append(key).append(":").append(NL).append(((Set<AbstractState>) statistics.get(key)).stream().map(abstractState -> indentLine(abstractState.toString())).collect(Collectors.joining()));
                    indentLeft();
                    break;
                case SET_UNRCHD_AT:
                    indentRight();
                    formatted.append(key).append(":").append(NL).append(((Set<AbstractTransition>) statistics.get(key)).stream().map(abstractTransition -> indentLine(abstractTransition.getSource().getName() + " -[ " + abstractTransition.getEvent().getName() + " ]-> " + abstractTransition.getTarget().getName())).collect(Collectors.joining()));
                    indentLeft();
                    break;
                /*case TESTS:
                    indentRight();
                    formatted.append(key).append(":").append(NL).append(((List<List<ConcreteTransition>>) statistics.get(key)).stream().map(test -> test.stream().map(concreteTransition -> indentation() + concreteTransition.getSource().getName() + " -[ " + concreteTransition.getEvent().getName() + " ]-> " + concreteTransition.getTarget().getName()).collect(Collectors.joining(NL))).collect(Collectors.joining(NL + indentation() + "---------------------" + NL)));
                    indentLeft();
                    break;*/
                default:
                    throw new Error("Unhandled statistics key \"" + key + "\".");
            }
            formatted.append(NL).append(NL);
        }
        return formatted.toString();
    }

}

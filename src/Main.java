import algs.AbstractStatesComputer;
import algs.CXPComputer;
import algs.outputs.ATS;
import formatters.graphs.DefaultGVZFormatter;
import graphs.AbstractState;
import langs.eventb.exprs.bool.Predicate;
import parsers.eventb.EventBParser;

import java.io.File;
import java.util.LinkedHashSet;

import static formatters.graphs.ERankDir.LR;

public class Main {

    public static void main(String[] args) {
        EventBParser.parseMachine(new File("resources/eventb/L14/L14.ebm"));
        LinkedHashSet<Predicate> ap0 = EventBParser.parseAPs(new File("resources/eventb/L14/APs/ap1.ap"));
        LinkedHashSet<AbstractState> as = new AbstractStatesComputer(ap0).compute();
        ATS compute = new CXPComputer(as).compute();
        System.out.println(compute.getMTS().accept(new DefaultGVZFormatter<>(false, LR)));
    }

}

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
        EventBParser.parseMachine(new File("resources/eventb/EL/EL.ebm"));
        LinkedHashSet<Predicate> ap1 = EventBParser.parseAPs(new File("resources/eventb/EL/APs/ap0.ap"));
        LinkedHashSet<AbstractState> as1 = new AbstractStatesComputer(ap1).compute();
        ATS ats1 = new CXPComputer(as1).compute();
        System.out.println(ats1.getMTS().accept(new DefaultGVZFormatter<>(false, LR)));
        EventBParser.parseMachine(new File("resources/eventb/L14/L14.ebm"));
        LinkedHashSet<Predicate> ap2 = EventBParser.parseAPs(new File("resources/eventb/L14/APs/ap1.ap"));
        LinkedHashSet<AbstractState> as2 = new AbstractStatesComputer(ap2).compute();
        ATS ats2 = new CXPComputer(as2).compute();
        System.out.println(ats2.getMTS().accept(new DefaultGVZFormatter<>(false, LR)));
    }

}

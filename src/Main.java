import algs.AbstractStatesComputer;
import graphs.AbstractState;
import langs.eventb.exprs.bool.Predicate;
import parsers.eventb.EventBParser;

import java.io.File;
import java.util.LinkedHashSet;

public class Main {

    public static void main(String[] args) {
        EventBParser.parseMachine(new File("resources/eventb/EL/EL.ebm"));
        LinkedHashSet<Predicate> ap0 = EventBParser.parseAPs(new File("resources/eventb/EL/APs/ap0.ap"));
        LinkedHashSet<AbstractState> compute = new AbstractStatesComputer(ap0).compute();
        System.out.println(compute);
    }

}

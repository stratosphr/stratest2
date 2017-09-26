import algs.FullFSMComputer;
import formatters.graphs.DefaultGVZFormatter;
import graphs.ConcreteState;
import graphs.FSM;
import langs.eventb.Event;
import parsers.eventb.MachineParser;

import java.io.File;

import static formatters.graphs.ERankDir.LR;

public class Main {

    public static void main(String[] args) {
        MachineParser.parse(new File("resources/eventb/EL/EL.ebm"));
        FSM<ConcreteState, Event> fsm = new FullFSMComputer().compute();
        //fsm.getTransitions().forEach(concreteTransition -> System.out.println(concreteTransition.toString().replaceAll(TAB, "").replaceAll(NL, " ")));
        System.out.println(fsm.accept(new DefaultGVZFormatter<>(false, LR)));
    }

}

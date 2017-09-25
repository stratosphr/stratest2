import algs.FullFSMComputer;
import graphs.ConcreteState;
import graphs.ConcreteTransition;
import graphs.FSM;
import langs.eventb.Machine;
import parsers.eventb.MachineParser;

import java.io.File;

import static utilities.Chars.NL;
import static utilities.Chars.TAB;

public class Main {

    public static void main(String[] args) {
        MachineParser.parse(new File("resources/eventb/EL/EL.ebm"));
        FSM<ConcreteState, ConcreteTransition> fsm = new FullFSMComputer().compute();
        System.out.println(fsm.getInitialStates().size() + " " + fsm.getStates().size() + " " + fsm.getTransitions().size());
        fsm.getTransitions().forEach(concreteTransition -> System.out.println(concreteTransition.toString().replaceAll(TAB, "").replaceAll(NL, " ")));
        System.out.println(Machine.getEvents().get("Tic").getSubstitution().getPrd(Machine.getAssignables()));
    }

}

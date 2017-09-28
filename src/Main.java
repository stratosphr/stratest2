import algs.AbstractStatesComputer;
import algs.CXPComputer;
import algs.FullFSMComputer;
import algs.RGCXPComputer;
import algs.heuristics.relevance.*;
import algs.outputs.ATS;
import graphs.AbstractState;
import graphs.ConcreteState;
import graphs.FSM;
import langs.eventb.Event;
import langs.eventb.exprs.arith.EnumValue;
import langs.eventb.exprs.arith.Fun;
import langs.eventb.exprs.arith.Int;
import langs.eventb.exprs.bool.Equals;
import langs.eventb.exprs.bool.Predicate;
import parsers.eventb.EventBParser;

import java.io.File;
import java.util.LinkedHashSet;

public class Main {

    public static void main(String[] args) {
        EventBParser.parseMachine(new File("resources/eventb/L14/L14.ebm"));
        LinkedHashSet<Predicate> ap2 = EventBParser.parseAPs(new File("resources/eventb/L14/APs/ap1.ap"));
        LinkedHashSet<AbstractState> as2 = new AbstractStatesComputer(ap2).compute();
        RelevancePredicate relevancePredicate = new RelevancePredicate(
                new AtomicPredicateEnumSet(new Fun("Mvt", new Int(1)), new Int(0), new Int(1)),
                new AtomicPredicateMultiImplies(
                        new AtomicPredicateImplies(new Equals(new Fun("Dir", new Int(1)), new Int(1)), new AtomicPredicateGT(new Fun("Pos", new Int(1)))),
                        new AtomicPredicateImplies(new Equals(new Fun("Dir", new Int(1)), new Int(-1)), new AtomicPredicateLT(new Fun("Pos", new Int(1))))
                ),
                new AtomicPredicateEnumSet(new Fun("Dir", new Int(1)), new Int(-1), new Int(1)),
                new AtomicPredicateEnumSet(new Fun("Dir", new Int(1)), new Int(1), new Int(-1)),
                new AtomicPredicateEnumSet(new Fun("Portes", new Int(1)), new EnumValue("fermees"), new EnumValue("ouvertes")),
                new AtomicPredicateEnumSet(new Fun("Portes", new Int(1)), new EnumValue("ouvertes"), new EnumValue("refermees")),
                new AtomicPredicateEnumSet(new Fun("Mvt", new Int(2)), new Int(0), new Int(1)),
                new AtomicPredicateMultiImplies(
                        new AtomicPredicateImplies(new Equals(new Fun("Dir", new Int(2)), new Int(1)), new AtomicPredicateGT(new Fun("Pos", new Int(2)))),
                        new AtomicPredicateImplies(new Equals(new Fun("Dir", new Int(2)), new Int(-1)), new AtomicPredicateLT(new Fun("Pos", new Int(2))))
                ),
                new AtomicPredicateEnumSet(new Fun("Dir", new Int(2)), new Int(-1), new Int(1)),
                new AtomicPredicateEnumSet(new Fun("Dir", new Int(2)), new Int(1), new Int(-1)),
                new AtomicPredicateEnumSet(new Fun("Portes", new Int(2)), new EnumValue("fermees"), new EnumValue("ouvertes")),
                new AtomicPredicateEnumSet(new Fun("Portes", new Int(2)), new EnumValue("ouvertes"), new EnumValue("refermees"))

        );
        ATS cxp = new CXPComputer(as2).compute();
        ATS rgcxp = new RGCXPComputer(cxp, relevancePredicate, 1000).compute();
        FSM<ConcreteState, Event> full = new FullFSMComputer().compute();
        System.out.println(rgcxp.getCTS().getC().size());
        System.out.println(full.getStates().size());
        /*System.out.println(cxp.getCTS().accept(new DefaultGVZFormatter<>(true, ERankDir.LR)));
        System.out.println(rgcxp.getCTS().accept(new DefaultGVZFormatter<>(true, ERankDir.LR)));*/
    }

}

import algs.heuristics.DefaultAbstractStatesOrderingFunction;
import algs.heuristics.DefaultEventsOrderingFunction;
import algs.heuristics.relevance.*;
import formatters.eventb.EventBFormatter;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.Equals;
import parsers.eventb.EventBParser;

import static algs.statistics.Saver.save;
import static utilities.Resources.*;

public class Main {

    public static void main(String[] args) {
        /*ev_ap1();
        ev_ap2();
        ev_ap3();
        cm_ap1();
        cm_ap2(); // Relevance may be improved (AT = 36% with relevance, AT = 40% with true)
        ca_ap1();
        ca_ap2();
        el_ap0();
        el_ap1();
        el_ap2();
        el_ap4();
        ph_ap1();
        ph_ap2();
        ph_ap3();
        ph_ap4();
        l14_ap1();
        l14_ap2();
        l14_ap3();*/
        el_ap0();
        ca_ap1();
        ca_ap2();
    }

    public static RelevancePredicate el() {
        return new RelevancePredicate(
                new AtomicPredicateEnumSet(new Fun("bat", new Int(1)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(2)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(3)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(4)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(5)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(6)), new EnumValue("ok"), new EnumValue("ko"))
        );
    }

    public static RelevancePredicate ev() {
        return new RelevancePredicate(
                new AtomicPredicateEnumSet(new Fun("PE", new Var("Pos")), new EnumValue("ouvertes"), new EnumValue("fermees")),
                new AtomicPredicateEnumSet(new Fun("PE", new Var("Pos")), new EnumValue("fermees"), new EnumValue("ouvertes")),
                new AtomicPredicateEnumSet(new Var("PC"), new EnumValue("fermees"), new EnumValue("ouvertes")),
                new AtomicPredicateEnumSet(new Var("PC"), new EnumValue("ouvertes"), new EnumValue("refermees")),
                new AtomicPredicateEnumSet(new Var("Dir"), new Int(1), new Int(-1)),
                new AtomicPredicateMultiImpliesV2(
                        new AtomicPredicateImplies(new Equals(new Var("Dir"), new Int(1)), new AtomicPredicateGT(new Var("Pos"))),
                        new AtomicPredicateImplies(new Equals(new Var("Dir"), new Int(-1)), new AtomicPredicateLT(new Var("Pos")))
                ),
                new AtomicPredicateEnumSet(new Fun("BM", new Plus(new Var("Pos"), new Int(1))), new Int(0), new Int(1)),
                new AtomicPredicateEnumSet(new Fun("BD", new Minus(new Var("Pos"), new Int(1))), new Int(0), new Int(1))
        );
        // Full : > 2570
    }

    public static RelevancePredicate cm() {
        return new RelevancePredicate(
                new AtomicPredicateGT(new Var("Balance")),
                new AtomicPredicateLT(new Var("CoffeeLeft")),
                new AtomicPredicateEnumSet(new Var("AskCoffee"), new Int(0), new Int(1))
        );
    }

    public static RelevancePredicate l14() {
        return new RelevancePredicate(
                new AtomicPredicateEnumSet(new Fun("Mvt", new Int(1)), new Int(0), new Int(1)),
                new AtomicPredicateMultiImpliesV2(
                        new AtomicPredicateImplies(new Equals(new Fun("Dir", new Int(1)), new Int(1)), new AtomicPredicateGT(new Fun("Pos", new Int(1)))),
                        new AtomicPredicateImplies(new Equals(new Fun("Dir", new Int(1)), new Int(-1)), new AtomicPredicateLT(new Fun("Pos", new Int(1))))
                ),
                new AtomicPredicateEnumSet(new Fun("Dir", new Int(1)), new Int(-1), new Int(1)),
                new AtomicPredicateEnumSet(new Fun("Dir", new Int(1)), new Int(1), new Int(-1)),
                new AtomicPredicateEnumSet(new Fun("Portes", new Int(1)), new EnumValue("fermees"), new EnumValue("ouvertes")),
                new AtomicPredicateEnumSet(new Fun("Portes", new Int(1)), new EnumValue("ouvertes"), new EnumValue("refermees")),
                new AtomicPredicateEnumSet(new Fun("Mvt", new Int(2)), new Int(0), new Int(1)),
                new AtomicPredicateMultiImpliesV2(
                        new AtomicPredicateImplies(new Equals(new Fun("Dir", new Int(2)), new Int(1)), new AtomicPredicateGT(new Fun("Pos", new Int(2)))),
                        new AtomicPredicateImplies(new Equals(new Fun("Dir", new Int(2)), new Int(-1)), new AtomicPredicateLT(new Fun("Pos", new Int(2))))
                ),
                new AtomicPredicateEnumSet(new Fun("Dir", new Int(2)), new Int(-1), new Int(1)),
                new AtomicPredicateEnumSet(new Fun("Dir", new Int(2)), new Int(1), new Int(-1)),
                new AtomicPredicateEnumSet(new Fun("Portes", new Int(2)), new EnumValue("fermees"), new EnumValue("ouvertes")),
                new AtomicPredicateEnumSet(new Fun("Portes", new Int(2)), new EnumValue("ouvertes"), new EnumValue("refermees"))

        );
    }

    private static RelevancePredicate ca() {
        return new RelevancePredicate(
                // ************************* //
                new AtomicPredicateLT(new Var("Us")),
                new AtomicPredicateGT(new Var("AC")),
                new AtomicPredicateGT(new Var("De")),
                // ************************* //
                new AtomicPredicateLT(new Var("Lo")),
                new AtomicPredicateGT(new Var("Gl")),
                // ************************* //
                new AtomicPredicateLT(new Var("AC"))
                // ************************* //
        );
    }

    private static RelevancePredicate ph() {
        return new RelevancePredicate(
                new AtomicPredicateGT(new Var("Enum"))
        );
    }

    private static void ca_ap1() {
        //save("1_true", EventBParser.parseMachine(EBM_CA), EventBParser.parseAPs(AP_CA_1), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(), new True(), 7000);
        EventBParser.parseMachine(EBM_CA);
        save("1_rel", EventBParser.parseAPs(AP_CA_1), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ca(),
                7000
        );
    }

    private static void ca_ap2() {
        EventBParser.parseMachine(EBM_CA);
        save("2_rel", EventBParser.parseAPs(AP_CA_2), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ca(),
                7000
        );
    }

    private static void cm_ap1() {
        EventBParser.parseMachine(EBM_CM);
        save("1", EventBParser.parseAPs(AP_CM_1), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                cm(),
                7000);
    }

    private static void cm_ap2() {
        EventBParser.parseMachine(EBM_CM);
        save("2", EventBParser.parseAPs(AP_CM_2), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                cm(),
                7000);
    }

    private static void el_ap0() {
        EventBParser.parseMachine(EBM_EL);
        System.out.println(EventBFormatter.format(new Machine()));
        System.exit(42);
        save("0_rel", EventBParser.parseAPs(AP_EL_0), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                el(),
                7000
        );
    }

    private static void el_ap1() {
        EventBParser.parseMachine(EBM_EL);
        save("1_rel", EventBParser.parseAPs(AP_EL_1), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                el(),
                7000
        );
    }

    private static void el_ap2() {
        EventBParser.parseMachine(EBM_EL);
        save("2_rel", EventBParser.parseAPs(AP_EL_2), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                el(),
                7000
        );
    }

    private static void el_ap4() {
        EventBParser.parseMachine(EBM_EL);
        save("4_rel", EventBParser.parseAPs(AP_EL_4), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                el(),
                7000
        );
    }

    private static void ev_ap1() {
        // True + limit ~= 2750 for 100%
        EventBParser.parseMachine(EBM_EV);
        save("1_rel", EventBParser.parseAPs(AP_EV_1), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ev(),
                1500
        );
    }

    private static void ev_ap2() {
        EventBParser.parseMachine(EBM_EV);
        save("2", EventBParser.parseAPs(AP_EV_2), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ev(),
                7000
        );
    }

    private static void ev_ap3() {
        EventBParser.parseMachine(EBM_EV);
        save("3_rel", EventBParser.parseAPs(AP_EV_3), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ev(),
                7000
        );
    }

    private static void l14_ap1() {
        EventBParser.parseMachine(EBM_L14);
        save("1_rel", EventBParser.parseAPs(AP_L14_1), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                l14(),
                7000
        );
    }

    private static void l14_ap2() {
        EventBParser.parseMachine(EBM_L14);
        save("2", EventBParser.parseAPs(AP_L14_2), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                l14(),
                7000);
    }

    private static void l14_ap3() {
        EventBParser.parseMachine(EBM_L14);
        save("3", EventBParser.parseAPs(AP_L14_3), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                l14(),
                7000);
    }

    private static void ph_ap1() {
        EventBParser.parseMachine(EBM_PH);
        save("1", EventBParser.parseAPs(AP_PH_1), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ph(),
                7000);
    }

    private static void ph_ap2() {
        EventBParser.parseMachine(EBM_PH);
        save("2", EventBParser.parseAPs(AP_PH_2), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ph(),
                7000);
    }

    private static void ph_ap3() {
        EventBParser.parseMachine(EBM_PH);
        save("3", EventBParser.parseAPs(AP_PH_3), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ph(),
                7000);
    }

    private static void ph_ap4() {
        EventBParser.parseMachine(EBM_PH);
        save("4", EventBParser.parseAPs(AP_PH_4), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ph(),
                7000);
    }

}

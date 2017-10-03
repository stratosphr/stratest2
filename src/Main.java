import algs.heuristics.DefaultAbstractStatesOrderingFunction;
import algs.heuristics.DefaultEventsOrderingFunction;
import algs.heuristics.relevance.*;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.Equals;
import parsers.eventb.EventBParser;

import static algs.statistics.Saver.save2;
import static utilities.Resources.*;

public class Main {

    public static void main(String[] args) {
        /*cm_ap0();
        cm_ap1();
        cm_ap2();
        el_ap0();
        el_ap1();
        el_ap2();
        el_ap4();
        ph_ap1();
        ph_ap2();
        ph_ap3();
        ph_ap4();
        ca_ap1();
        ca_ap2();
        l14_ap1();
        l14_ap2();
        l14_ap3();
        ev_ap1();
        ev_ap2();
        ev_ap3();*/
        ev_ap1(true);
    }

    public static RelevancePredicate el() {
        return new RelevancePredicate(
                new AtomicPredicateEnumSet(new Fun("bat", new Int(1)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(2)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(3)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(4)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(5)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(6)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(7)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(8)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(9)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(10)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(11)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(12)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(13)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(14)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(15)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(16)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(17)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(18)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(19)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(20)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(21)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(22)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(23)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(24)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(25)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(26)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(27)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(28)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(29)), new EnumValue("ok"), new EnumValue("ko")),
                new AtomicPredicateEnumSet(new Fun("bat", new Int(30)), new EnumValue("ok"), new EnumValue("ko"))
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
        // Full : = 2944
    }

    public static RelevancePredicate cm() {
        return new RelevancePredicate(
                new AtomicPredicateGT(new Var("Balance")),
                new AtomicPredicateLT(new Var("CoffeeLeft")),
                new AtomicPredicateEnumSet(new Var("AskCoffee"), new Int(0), new Int(1)),
                new AtomicPredicateLT(new Var("Pot")),
                new AtomicPredicateEnumSet(new Var("Status"), new EnumValue("on"), new EnumValue("off"))
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
                new AtomicPredicateGT(new Var("Etat"))
        );
    }

    private static void ca_ap1() {
        //save2("1_true", EventBParser.parseMachine(EBM_CA), EventBParser.parseAPs(AP_CA_1), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(), new True(), 7000);
        EventBParser.parseMachine(EBM_CA);
        save2("1_rel", EventBParser.parseAPs(AP_CA_1), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ca(),
                7000
        );
    }

    private static void ca_ap2() {
        EventBParser.parseMachine(EBM_CA);
        save2("2_rel", EventBParser.parseAPs(AP_CA_2), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ca(),
                7000
        );
    }

    private static void cm_ap0(boolean computeFull) {
        EventBParser.parseMachine(EBM_CM);
        save2("4-6-300", EventBParser.parseAPs(AP_CM_0), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                cm(),
                7000, computeFull);
    }

    private static void cm_ap1() {
        EventBParser.parseMachine(EBM_CM);
        save2("1", EventBParser.parseAPs(AP_CM_1), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                cm(),
                7000);
    }

    private static void cm_ap2() {
        EventBParser.parseMachine(EBM_CM);
        save2("2", EventBParser.parseAPs(AP_CM_2), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                cm(),
                7000);
    }

    private static void el_ap0(boolean computeFull) {
        EventBParser.parseMachine(EBM_EL);
        save2("2bat", EventBParser.parseAPs(AP_EL_0), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                el(),
                7000,
                computeFull
        );
    }

    private static void el_ap1() {
        EventBParser.parseMachine(EBM_EL);
        save2("1_rel", EventBParser.parseAPs(AP_EL_1), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                el(),
                7000
        );
    }

    private static void el_ap2() {
        EventBParser.parseMachine(EBM_EL);
        save2("2_rel", EventBParser.parseAPs(AP_EL_2), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                el(),
                7000
        );
    }

    private static void el_ap4() {
        EventBParser.parseMachine(EBM_EL);
        save2("4_rel", EventBParser.parseAPs(AP_EL_4), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                el(),
                7000
        );
    }

    private static void ev_ap1(boolean computeFull) {
        // 2944 with full
        EventBParser.parseMachine(EBM_EV);
        save2("1_rel", EventBParser.parseAPs(AP_EV_1), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ev(),
                1500,
                computeFull
        );
    }

    private static void ev_ap2() {
        EventBParser.parseMachine(EBM_EV);
        save2("2", EventBParser.parseAPs(AP_EV_2), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ev(),
                7000
        );
    }

    private static void ev_ap3() {
        EventBParser.parseMachine(EBM_EV);
        save2("3_rel", EventBParser.parseAPs(AP_EV_3), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ev(),
                7000
        );
    }

    private static void l14_ap1() {
        EventBParser.parseMachine(EBM_L14);
        save2("1_rel", EventBParser.parseAPs(AP_L14_1), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                l14(),
                7000
        );
    }

    private static void l14_ap2() {
        EventBParser.parseMachine(EBM_L14);
        save2("2", EventBParser.parseAPs(AP_L14_2), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                l14(),
                7000);
    }

    private static void l14_ap3() {
        EventBParser.parseMachine(EBM_L14);
        save2("3", EventBParser.parseAPs(AP_L14_3), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                l14(),
                7000);
    }

    private static void ph_ap1() {
        EventBParser.parseMachine(EBM_PH);
        save2("1", EventBParser.parseAPs(AP_PH_1), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ph(),
                7000);
    }

    private static void ph_ap2() {
        EventBParser.parseMachine(EBM_PH);
        save2("2", EventBParser.parseAPs(AP_PH_2), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ph(),
                7000);
    }

    private static void ph_ap3() {
        EventBParser.parseMachine(EBM_PH);
        save2("3", EventBParser.parseAPs(AP_PH_3), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ph(),
                7000);
    }

    private static void ph_ap4() {
        EventBParser.parseMachine(EBM_PH);
        save2("4", EventBParser.parseAPs(AP_PH_4), new DefaultAbstractStatesOrderingFunction(), new DefaultEventsOrderingFunction(),
                ph(),
                7000);
    }

}

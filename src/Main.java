import langs.eventb.exprs.bool.Predicate;
import parsers.eventb.EventBParser;

import java.io.File;
import java.util.LinkedHashSet;

public class Main {

    public static void main(String[] args) {
        EventBParser.parseMachine(new File("resources/eventb/L14/L14.ebm"));
        LinkedHashSet<Predicate> ap1 = EventBParser.parseAPs(new File("resources/eventb/L14/APs/ap1.ap"));
        LinkedHashSet<Predicate> ap2 = EventBParser.parseAPs(new File("resources/eventb/L14/APs/ap2.ap"));
        LinkedHashSet<Predicate> ap3 = EventBParser.parseAPs(new File("resources/eventb/L14/APs/ap3.ap"));
        System.out.println(ap1);
        System.out.println(ap2);
        System.out.println(ap3);
    }

}

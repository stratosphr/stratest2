import formatters.smt.SMT2Formatter;
import langs.eventb.Event;
import langs.eventb.Machine;
import langs.eventb.exprs.bool.And;
import parsers.eventb.EventBParser;

import java.io.File;
import java.util.LinkedHashSet;

public class Main {

    public static void main(String[] args) {
        Machine machine = EventBParser.parse(new File("resources/eventb/L14/L14.ebm"));
        Event fermeture_portes = machine.getEvents().stream().filter(event -> event.getName().equals("Fermeture_Portes")).findFirst().orElseGet(null);
        System.out.println(fermeture_portes);
        System.out.println(SMT2Formatter.format(new And(
                machine.getInvariant(),
                machine.getInvariant().prime(),
                fermeture_portes.getSubstitution().getPrd(new LinkedHashSet<>()))
        ));
    }

}

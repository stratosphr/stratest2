import langs.eventb.Event;
import langs.eventb.Machine;
import parsers.eventb.EventBParser;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        Machine machine = EventBParser.parse(new File("resources/eventb/L14/L14.ebm"));
        Event fermeture_portes = machine.getEvents().stream().filter(event -> event.getName().equals("Fermeture_Portes")).findFirst().orElseGet(null);
        System.out.println(machine.getInvariant());
    }

}

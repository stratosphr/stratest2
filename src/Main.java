import parsers.eventb.MachineParser;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        MachineParser.parse(new File("resources/eventb/EL/EL.ebm"));
    }

}

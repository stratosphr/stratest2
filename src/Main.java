import parsers.eventb.EventBParser;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.out.println(EventBParser.parse(new File("resources/eventb/L14/L14.ebm")));
    }

}

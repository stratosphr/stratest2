package parsers.eventb;

import parsers.xml.XMLParser;

import java.io.File;

/**
 * Created by gvoiron on 20/09/17.
 * Time : 16:01
 */
public final class EventBParser {

    private EventBParser() {
    }

    public static String parse(File file) {
        return XMLParser.parse(file, new File("resources/eventb/eventb.xsd")).toString();
    }

}

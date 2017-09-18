import parsers.xml.XMLDocument;
import parsers.xml.XMLParser;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        XMLDocument parse = XMLParser.parse(new File("resources/eventb/sample.ebm"), new File("resources/eventb/eventb.xsd"));
        System.out.println(parse);
    }

}

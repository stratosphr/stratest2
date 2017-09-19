import parsers.xml.XMLDocument;
import parsers.xml.XMLNode;
import parsers.xml.XMLParser;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        XMLDocument parse = XMLParser.parse(new File("resources/eventb/sample.ebm"), new File("resources/eventb/eventb.xsd"));
        List<XMLNode> children = parse.getRoot().getChildren("funs-defs").get(0).getChildren("fun-def");
        children.forEach(System.out::println);
    }

}

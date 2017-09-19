package formatters.xml;

import formatters.AFormatter;
import parsers.xml.XMLDocument;
import parsers.xml.XMLNode;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 09:42
 */
public final class XMLFormatter extends AFormatter implements IXMLVisitor {

    private XMLFormatter() {
    }

    public static String format(XMLDocument document) {
        XMLFormatter formatter = new XMLFormatter();
        return document.accept(formatter);
    }

    public static String format(XMLNode node) {
        XMLFormatter formatter = new XMLFormatter();
        return node.accept(formatter);
    }

    @Override
    public String visit(XMLDocument document) {
        return document.getRoot().accept(this);
    }

    @Override
    public String visit(XMLNode node) {
        /*line("<" + node.getName() + (node.getAttributes().isEmpty() ? "" : " " + node.getAttributes().keySet().stream().map(key -> key + "=\"" + node.getAttributes().get(key) + "\"").collect(Collectors.joining(" "))) + (node.getChildren().isEmpty() ? "/>" : ">"));
        if (!node.getChildren().isEmpty()) {
            indentRight();
            node.getChildren().forEach(child -> child.accept(this));
            indentLeft();
            line("</" + node.getName() + ">");
        }*/
        return "";
    }

}

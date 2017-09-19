package visitors.xml;

import parsers.xml.XMLDocument;
import parsers.xml.XMLNode;
import visitors.AFormatter;

import java.util.stream.Collectors;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 09:42
 */
public final class XMLFormatter extends AFormatter implements IXMLVisitor {

    private XMLFormatter() {
    }

    public static String format(XMLDocument document) {
        XMLFormatter formatter = new XMLFormatter();
        document.accept(formatter);
        return formatter.getFormatted();
    }

    public static String format(XMLNode node) {
        XMLFormatter formatter = new XMLFormatter();
        node.accept(formatter);
        return formatter.getFormatted();
    }

    @Override
    public void visit(XMLDocument document) {
        document.getRoot().accept(this);
    }

    @Override
    public void visit(XMLNode node) {
        formatLine("<" + node.getName() + (node.getAttributes().isEmpty() ? "" : " " + node.getAttributes().keySet().stream().map(key -> key + "=\"" + node.getAttributes().get(key) + "\"").collect(Collectors.joining(" "))) + (node.getChildren().isEmpty() ? "/>" : ">"));
        if (!node.getChildren().isEmpty()) {
            indentRight();
            node.getChildren().forEach(child -> child.accept(this));
            indentLeft();
            formatLine("</" + node.getName() + ">");
        }
    }

}

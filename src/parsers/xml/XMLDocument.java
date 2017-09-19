package parsers.xml;

import org.w3c.dom.Document;
import visitors.xml.IXMLVisitable;
import visitors.xml.IXMLVisitor;
import visitors.xml.XMLFormatter;

/**
 * Created by gvoiron on 18/09/17.
 * Time : 10:12
 */
public final class XMLDocument implements IXMLVisitable {

    private final XMLNode root;

    XMLDocument(Document document) {
        document.getDocumentElement().normalize();
        this.root = new XMLNode(document.getDocumentElement());
    }

    @Override
    public void accept(IXMLVisitor visitor) {
        visitor.visit(this);
    }

    public XMLNode getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return XMLFormatter.format(this);
    }

}

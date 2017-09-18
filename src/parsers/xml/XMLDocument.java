package parsers.xml;

import org.w3c.dom.Document;

/**
 * Created by gvoiron on 18/09/17.
 * Time : 10:12
 */
public final class XMLDocument {

    private final XMLNode root;

    public XMLDocument(Document document) {
        document.getDocumentElement().normalize();
        this.root = new XMLNode(document.getDocumentElement());
    }

}

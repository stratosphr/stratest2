package visitors.xml;

import parsers.xml.XMLDocument;
import parsers.xml.XMLNode;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 09:43
 */
public interface IXMLVisitor {

    void visit(XMLDocument xmlDocument);

    void visit(XMLNode xmlNode);

}

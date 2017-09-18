package parsers.xml;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Created by gvoiron on 18/09/17.
 * Time : 10:06
 */
public final class ErrorHandler implements org.xml.sax.ErrorHandler {

    public static String getErrorMessage(SAXParseException e) {
        return "XML file \"" + e.getSystemId().replaceAll("^file:", "") + "\" is invalid:\n" + "\tl." + e.getLineNumber() + ", c." + e.getColumnNumber() + ": " + e.getMessage().replaceAll("^.* : ", "");
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        throw new Error(getErrorMessage(e));
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        throw new Error(getErrorMessage(e));
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        throw new Error(getErrorMessage(e));
    }

}

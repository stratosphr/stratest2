package visitors.xml;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 09:42
 */
public interface IXMLVisitable {

    void accept(IXMLVisitor visitor);

}

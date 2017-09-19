package formatters.smt;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 12:12
 */
public interface ISMT2Visitable {

    String accept(ISMT2Visitor visitor);

}

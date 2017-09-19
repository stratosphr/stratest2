package formatters.eventb;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 10:42
 */
public interface IEventBVisitable {

    String accept(IEventBVisitor visitor);

}

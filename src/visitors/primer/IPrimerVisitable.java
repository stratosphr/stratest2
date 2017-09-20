package visitors.primer;

/**
 * Created by gvoiron on 20/09/17.
 * Time : 14:49
 */
public interface IPrimerVisitable<T> {

    T accept(IPrimerVisitor visitor);

}

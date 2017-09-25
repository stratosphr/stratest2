package graphs;

import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.APredicate;

import java.util.TreeMap;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 16:03
 */
public abstract class AState<K extends Comparable, V> extends APredicate {

    private final TreeMap<K, V> mapping;

    public AState(String name, ABoolExpr expr, TreeMap<K, V> mapping) {
        super(name, expr);
        this.mapping = mapping;
    }

    public TreeMap<K, V> getMapping() {
        return mapping;
    }

}

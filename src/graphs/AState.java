package graphs;

import langs.eventb.exprs.bool.ABoolExpr;
import langs.eventb.exprs.bool.APredicate;

import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * Created by gvoiron on 25/09/17.
 * Time : 16:03
 */
public abstract class AState<K extends Comparable, V> extends APredicate {

    protected final TreeMap<K, V> mapping;
    protected final LinkedHashMap<String, Object> markers;

    public AState(String name, ABoolExpr expr, TreeMap<K, V> mapping) {
        super(name, expr);
        this.mapping = mapping;
        this.markers = new LinkedHashMap<>();
    }

    public TreeMap<K, V> getMapping() {
        return mapping;
    }

    @Override
    public abstract AState clone();

    public void addMarker(String key, Object value) {
        markers.put(key, value);
    }

    public final LinkedHashMap<String, Object> getMarkers() {
        return markers;
    }

}

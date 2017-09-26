package formatters.graphs;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gvoiron on 26/09/17.
 * Time : 14:44
 */
public abstract class AGVZNode<T extends AGVZNode> {

    protected final Map<Object, Object> parameters;
    protected String comment;

    public AGVZNode() {
        this.parameters = new LinkedHashMap<>();
        this.comment = "";
    }

    protected abstract T getThis();

    protected T numberParameter(Object key, Object value) {
        parameters.put(key, value);
        return getThis();
    }

    protected T quoteParameter(Object key, Object value) {
        parameters.put(key, "\"" + value + "\"");
        return getThis();
    }

    protected T htmlParameter(Object key, Object value) {
        parameters.put(key, "<" + value + ">");
        return getThis();
    }

    public T setLabel(Object label) {
        return setLabel(label, false);
    }

    public T setLabel(Object label, boolean isHTML) {
        return isHTML ? htmlParameter("label", label) : quoteParameter("label", label);
    }

    public T setShape(Object shape) {
        return quoteParameter("shape", shape);
    }

    public T setStyle(Object style) {
        return quoteParameter("style", style);
    }

    public T setFillColor(Object fillColor) {
        return quoteParameter("fillcolor", fillColor);
    }

    public T setColor(Object color) {
        return quoteParameter("color", color);
    }

    public T setPenWidth(Integer penWidth) {
        return numberParameter("penwidth", penWidth.toString());
    }

    public T setWeight(double weight) {
        return numberParameter("weight", String.valueOf(weight));
    }

    public T setComment(Object s) {
        comment = s.toString();
        return getThis();
    }

    @Override
    public abstract String toString();

}

package visitors;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 11:36
 */
public abstract class AFormatter {

    private int indentation;
    private StringBuilder formatted;

    protected AFormatter() {
        this.indentation = 0;
        this.formatted = new StringBuilder();
    }

    protected void indentLeft() {
        if (indentation >= 1) {
            --indentation;
        }
    }

    protected void indentRight() {
        ++indentation;
    }

    private String indent() {
        return String.join("", Collections.nCopies(indentation, "\t"));
    }

    protected void format(String text) {
        formatted.append(indent()).append(text);
    }

    protected void formatLine(String line) {
        formatted.append(indent()).append(line).append("\n");
    }

    protected void join(Collection<?> operands, String join) {
        formatted.append(operands.stream().map(Object::toString).collect(Collectors.joining(join)));
    }

    protected String getFormatted() {
        return formatted.toString();
    }

}

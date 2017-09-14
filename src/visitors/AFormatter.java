package visitors;

import java.util.Collections;

/**
 * Created by gvoiron on 14/09/17.
 * Time : 11:36
 */
public abstract class AFormatter<T> {

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
        formatted.append(indent()).append(text).append("\n");
    }

    protected void formatLine(String line) {
        formatted.append(indent()).append(line).append("\n");
    }

    protected StringBuilder getFormatted() {
        return formatted;
    }

}

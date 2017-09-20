package formatters.eventb;

import formatters.eventb.exprs.AExprFormatter;
import langs.eventb.substitutions.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 19/09/17.
 * Time : 10:47
 */
public final class EventBFormatter extends AExprFormatter implements IEventBVisitor {

    private EventBFormatter() {
    }

    public static String format(IEventBVisitable visitable) {
        EventBFormatter formatter = new EventBFormatter();
        return visitable.accept(formatter);
    }

    @Override
    public String visit(Skip skip) {
        return "SKIP";
    }

    @Override
    public String visit(Assignment assignment) {
        return assignment.getAssignable().accept(this) + " := " + assignment.getValue().accept(this);
    }

    @Override
    public String visit(Assignments assignments) {
        return assignments.getAssignments().size() == 1 ? assignments.getAssignments().iterator().next().accept(this) : line(assignments.getAssignments().iterator().next().accept(this) + " ||") + new ArrayList<>(assignments.getAssignments()).subList(1, assignments.getAssignments().size()).stream().map(assignment -> indent(assignment.accept(this))).collect(Collectors.joining(" ||\n"));
    }

    @Override
    public String visit(Select select) {
        return line("SELECT") + indentRight() + indentLine(select.getCondition().accept(this)) + indentLeft() + indentLine("THEN") + indentRight() + indentLine(select.getSubstitution().accept(this)) + indentLeft() + indent("END");
    }

    @Override
    public String visit(IfThenElse ifThenElse) {
        return line("IF") + indentRight() + indentLine(ifThenElse.getCondition().accept(this)) + indentLeft() + indentLine("THEN") + indentRight() + indentLine(ifThenElse.getThenPart().accept(this)) + indentLeft() + indentLine("ELSE") + indentRight() + indentLine(ifThenElse.getElsePart().accept(this)) + indentLeft() + indent("END");
    }

    @Override
    public String visit(Any any) {
        return line("ANY") + indentRight() + any.getQuantifiedVars().stream().map(var -> indentLine(var.accept(this))).collect(Collectors.joining()) + indentLeft() + indentLine("WHERE") + indentRight() + indentLine(any.getCondition().accept(this)) + indentLeft() + indentLine("THEN") + indentRight() + indentLine(any.getSubstitution().accept(this)) + indentLeft() + indent("END");
    }

}

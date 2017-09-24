package formatters.eventb;

import formatters.eventb.exprs.AExprFormatter;
import langs.eventb.Event;
import langs.eventb.Machine;
import langs.eventb.substitutions.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static utilities.Chars.*;

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
    public String visit(Machine machine) {
        String formatted = line("MACHINE " + Machine.getName());
        formatted += Machine.getConstsDefs().isEmpty() ? "" : line() + indentRight() + indentLine("CONSTS") + indentRight() + Machine.getConsts().keySet().stream().map(name -> indentLine(Machine.getConsts().get(name).accept(this) + " " + EQ_DEF + " " + Machine.getConstsDefs().get(name).accept(this))).collect(Collectors.joining()) + indentLeft() + indentLeft();
        formatted += Machine.getSetsDefs().isEmpty() ? "" : line() + indentRight() + indentLine("SETS") + indentRight() + Machine.getSets().keySet().stream().map(name -> indentLine(name + " " + EQ_DEF + " " + Machine.getSets().get(name).accept(this))).collect(Collectors.joining()) + indentLeft() + indentLeft();
        //formatted += machine.getVarsDefs().isEmpty() ? "" : line() + indentRight() + indentLine("VARS") + indentRight() + machine.getVars().keySet().stream().map(name -> indentLine(new Var(name).accept(this) + " " + IN + " " + machine.getVarsDefs().get(name).accept(this))).collect(Collectors.joining()) + indentLeft() + indentLeft();
        formatted += Machine.getFunsDefs().isEmpty() ? "" : line() + indentRight() + indentLine("FUNS") + indentRight() + Machine.getFunsDefs().keySet().stream().map(name -> indentLine(name + " : " + Machine.getFunsDefs().get(name).getFirst().accept(this) + " -> " + Machine.getFunsDefs().get(name).getSecond().accept(this))).collect(Collectors.joining()) + indentLeft() + indentLeft();
        formatted += line() + indentRight() + indentLine("INVARIANT") + indentRight() + indentLine(Machine.getInvariant().accept(this)) + indentLeft() + indentLeft();
        formatted += line() + indentRight() + indentLine("INITIALISATION") + indentRight() + indentLine(Machine.getInitialisation().accept(this)) + indentLeft() + indentLeft();
        formatted += line() + indentRight() + indentLine("EVENTS") + indentRight() + Machine.getEvents().stream().map(event -> indentLine(event.accept(this))).collect(Collectors.joining()) + indentLeft() + indentLeft();
        return formatted;
    }

    @Override
    public String visit(Event event) {
        return line(event.getName() + " " + EQ_DEF) + indentRight() + indentLine(event.getSubstitution().accept(this)) + indentLeft();
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
        return assignments.getAssignments().size() == 1 ? assignments.getAssignments().iterator().next().accept(this) : line(assignments.getAssignments().iterator().next().accept(this) + " ||") + new ArrayList<>(assignments.getAssignments()).subList(1, assignments.getAssignments().size()).stream().map(assignment -> indent(assignment.accept(this))).collect(Collectors.joining(" ||" + NL));
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
        return line("ANY") + indentRight() + any.getQuantifiedVarsDefs().stream().map(tuple -> indentLine(tuple.getFirst().accept(this) + " " + IN + " " + tuple.getSecond().accept(this))).collect(Collectors.joining()) + indentLeft() + indentLine("WHERE") + indentRight() + indentLine(any.getCondition().accept(this)) + indentLeft() + indentLine("THEN") + indentRight() + indentLine(any.getSubstitution().accept(this)) + indentLeft() + indent("END");
    }

}

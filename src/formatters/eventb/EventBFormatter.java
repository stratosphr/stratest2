package formatters.eventb;

import formatters.eventb.exprs.AExprFormatter;
import langs.eventb.Event;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.Var;
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
    public String visit(Machine machine) {
        String formatted = line("MACHINE" + machine.getName());
        formatted += machine.getConstsDefs().isEmpty() ? "" : line() + indentRight() + indentLine("CONSTS") + indentRight() + machine.getConsts().keySet().stream().map(name -> indentLine(machine.getConsts().get(name).accept(this) + " =def " + machine.getConstsDefs().get(name).accept(this))).collect(Collectors.joining()) + indentLeft() + indentLeft();
        formatted += machine.getVarsDefs().isEmpty() ? "" : line() + indentRight() + indentLine("VARS") + indentRight() + machine.getVars().keySet().stream().map(name -> indentLine(new Var(name).accept(this) + " in " + machine.getVarsDefs().get(name).accept(this))).collect(Collectors.joining()) + indentLeft() + indentLeft();
        formatted += machine.getFunsDefs().isEmpty() ? "" : line() + indentRight() + indentLine("FUNS") + indentRight() + machine.getFunsDefs().keySet().stream().map(name -> indentLine(name + " : " + machine.getFunsDefs().get(name).getFirst().accept(this) + " -> " + machine.getFunsDefs().get(name).getSecond().accept(this))).collect(Collectors.joining()) + indentLeft() + indentLeft();
        formatted += line() + indentRight() + indentLine("INVARIANT") + indentRight() + indentLine(machine.getInvariant().accept(this)) + indentLeft() + indentLeft();
        formatted += line() + indentRight() + indentLine("INITIALISATION") + indentRight() + indentLine(machine.getInitialisation().accept(this)) + indentLeft() + indentLeft();
        formatted += line() + indentRight() + indentLine("EVENTS") + indentRight() + machine.getEvents().stream().map(event -> indentLine(event.accept(this))).collect(Collectors.joining()) + indentLeft() + indentLeft();
        return formatted;
    }

    @Override
    public String visit(Event event) {
        return line(event.getName() + " =def ") + indentRight() + indentLine(event.getSubstitution().accept(this)) + indentLeft();
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
        return line("ANY") + indentRight() + any.getQuantifiedVarsDefs().stream().map(tuple -> indentLine(tuple.getFirst().accept(this) + " in " + tuple.getSecond().accept(this))).collect(Collectors.joining()) + indentLeft() + indentLine("WHERE") + indentRight() + indentLine(any.getCondition().accept(this)) + indentLeft() + indentLine("THEN") + indentRight() + indentLine(any.getSubstitution().accept(this)) + indentLeft() + indent("END");
    }

}

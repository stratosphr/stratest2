package parsers.eventb;

import langs.eventb.Event;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.*;
import langs.eventb.exprs.bool.NEQ;
import langs.eventb.exprs.sets.*;
import langs.eventb.exprs.sets.Enum;
import langs.eventb.substitutions.*;
import parsers.xml.XMLDocument;
import parsers.xml.XMLNode;
import parsers.xml.XMLParser;
import utilities.sets.Tuple2;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import static parsers.eventb.EEBMAttributes.NAME;
import static parsers.eventb.EEBMAttributes.VALUE;
import static parsers.eventb.EEBMElements.*;

/**
 * Created by gvoiron on 20/09/17.
 * Time : 16:01
 */
public final class EventBParser {

    private EventBParser() {
    }

    private void check(XMLNode node, String... expected) throws Error {
        if (!Arrays.asList(expected).contains(node.getName())) {
            throw new Error("Node \"" + node.getName() + "\" was expected to be one of the following: " + Arrays.stream(expected).collect(Collectors.joining(", ")));
        }
    }

    public static void parseMachine(File file) {
        Machine.reset();
        EventBParser parser = new EventBParser();
        XMLDocument parse = XMLParser.parse(file, new File("resources/eventb/eventb.xsd"));
        parser.parseModel(parse.getRoot());
    }

    public static LinkedHashSet<Predicate> parseAPs(File file) {
        EventBParser parser = new EventBParser();
        XMLDocument parse = XMLParser.parse(file, new File("resources/eventb/ap.xsd"));
        return parser.parsePredicates(parse.getRoot());
    }

    private void parseModel(XMLNode root) {
        check(root, MODEL);
        List<XMLNode> constsDefsNode = root.getChildren(CONSTS_DEFS);
        List<XMLNode> setsDefsNode = root.getChildren(SETS_DEFS);
        List<XMLNode> varsDefsNode = root.getChildren(VARS_DEFS);
        List<XMLNode> funsDefsNode = root.getChildren(FUNS_DEFS);
        List<XMLNode> invariantNode = root.getChildren(INVARIANT);
        List<XMLNode> initialisationNode = root.getChildren(INITIALISATION);
        List<XMLNode> eventsNode = root.getChildren(EVENTS);
        Machine.setName(root.getAttributes().get(NAME));
        if (!constsDefsNode.isEmpty()) {
            parseConstsDefs(constsDefsNode.get(0)).forEach(tuple -> Machine.addConstDef(tuple.getFirst(), tuple.getSecond()));
        }
        if (!setsDefsNode.isEmpty()) {
            parseSetsDefs(setsDefsNode.get(0)).forEach(tuple -> Machine.addSetDef(tuple.getFirst(), tuple.getSecond()));
        }
        if (!varsDefsNode.isEmpty()) {
            parseVarsDefs(varsDefsNode.get(0)).forEach(tuple -> Machine.addVarDef(tuple.getFirst(), tuple.getSecond()));
        }
        if (!funsDefsNode.isEmpty()) {
            parseFunsDefs(funsDefsNode.get(0)).forEach(tuple -> Machine.addFunDef(tuple.getFirst(), tuple.getSecond()));
        }
        if (!invariantNode.isEmpty()) {
            Machine.setInvariant(parseInvariant(invariantNode.get(0)));
        }
        if (!initialisationNode.isEmpty()) {
            Machine.setInitialisation(parseInitialisation(initialisationNode.get(0)));
        }
        if (!eventsNode.isEmpty()) {
            parseEvents(eventsNode.get(0)).forEach(Machine::addEvent);
        }
    }

    private LinkedHashSet<Predicate> parsePredicates(XMLNode root) {
        check(root, PREDICATES);
        return root.getChildren().stream().map(this::parsePredicate).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private LinkedHashSet<Tuple2<String, AArithExpr>> parseConstsDefs(XMLNode node) {
        check(node, CONSTS_DEFS);
        return node.getChildren().stream().map(this::parseConstDef).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Tuple2<String, AArithExpr> parseConstDef(XMLNode node) {
        check(node, CONST_DEF);
        return new Tuple2<>(node.getAttributes().get(NAME), parseArithExpr(node.getChildren().get(0)));
    }

    private LinkedHashSet<Tuple2<String, ASetExpr>> parseSetsDefs(XMLNode node) {
        check(node, SETS_DEFS);
        return node.getChildren().stream().map(this::parseSetDef).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Tuple2<String, ASetExpr> parseSetDef(XMLNode node) {
        check(node, SET_DEF);
        return new Tuple2<>(node.getAttributes().get(NAME), parseSetExpr(node.getChildren().get(0)));
    }

    private LinkedHashSet<Tuple2<String, ASetExpr>> parseVarsDefs(XMLNode node) {
        check(node, VARS_DEFS);
        return node.getChildren().stream().map(this::parseVarDef).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Tuple2<String, ASetExpr> parseVarDef(XMLNode node) {
        check(node, VAR_DEF);
        return new Tuple2<>(node.getAttributes().get(NAME), parseSetExpr(node.getChildren().get(0)));
    }

    private LinkedHashSet<Tuple2<String, Tuple2<ASetExpr, ASetExpr>>> parseFunsDefs(XMLNode node) {
        check(node, FUNS_DEFS);
        return node.getChildren().stream().map(this::parseFunDef).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Tuple2<String, Tuple2<ASetExpr, ASetExpr>> parseFunDef(XMLNode node) {
        check(node, FUN_DEF);
        return new Tuple2<>(node.getAttributes().get(NAME), new Tuple2<>(parseSetExpr(node.getChildren().get(0)), parseSetExpr(node.getChildren().get(1))));
    }

    private Invariant parseInvariant(XMLNode node) {
        check(node, INVARIANT);
        return new Invariant(parseBoolExpr(node.getChildren().get(0)));
    }

    private ASubstitution parseInitialisation(XMLNode node) {
        check(node, INITIALISATION);
        return parseSubstitution(node.getChildren().get(0));
    }

    private LinkedHashSet<Event> parseEvents(XMLNode node) {
        check(node, EVENTS);
        return node.getChildren().stream().map(this::parseEvent).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Event parseEvent(XMLNode node) {
        check(node, EVENT);
        return new Event(node.getAttributes().get(NAME), parseSubstitution(node.getChildren().get(0)));
    }

    private AArithExpr parseArithExpr(XMLNode node) {
        switch (node.getName()) {
            case INT:
                return parseInt(node);
            case CONST:
                return parseConst(node);
            case ENUM_VALUE:
                return parseEnumValue(node);
            case VAR:
                return parseVar(node);
            case FUN:
                return parseFun(node);
            case PLUS:
                return parsePlus(node);
            case MINUS:
                return parseMinus(node);
            case TIMES:
                return parseTimes(node);
            case DIV:
                return parseDiv(node);
            default:
                check(node, INT, CONST, VAR, FUN, PLUS, MINUS, TIMES, DIV);
                return null;
        }
    }

    private AAssignable parseAssignable(XMLNode node) {
        switch (node.getName()) {
            case VAR:
                return parseVar(node);
            case FUN:
                return parseFun(node);
            default:
                check(node, VAR, FUN);
                return null;
        }
    }

    private AArithExpr parseInt(XMLNode node) {
        check(node, INT);
        return new Int(Integer.parseInt(node.getAttributes().get(VALUE)));
    }

    private AArithExpr parseConst(XMLNode node) {
        check(node, CONST);
        return new Const(node.getAttributes().get(NAME));
    }

    private Var parseVar(XMLNode node) {
        check(node, VAR);
        return new Var(node.getAttributes().get(NAME));
    }

    private Fun parseFun(XMLNode node) {
        check(node, FUN);
        return new Fun(node.getAttributes().get(NAME), parseArithExpr(node.getChildren().get(0)));
    }

    private AArithExpr parsePlus(XMLNode node) {
        check(node, PLUS);
        return new Plus(node.getChildren().stream().map(this::parseArithExpr).toArray(AArithExpr[]::new));
    }

    private AArithExpr parseMinus(XMLNode node) {
        check(node, MINUS);
        return new Minus(node.getChildren().stream().map(this::parseArithExpr).toArray(AArithExpr[]::new));
    }

    private AArithExpr parseTimes(XMLNode node) {
        check(node, TIMES);
        return new Times(node.getChildren().stream().map(this::parseArithExpr).toArray(AArithExpr[]::new));
    }

    private AArithExpr parseDiv(XMLNode node) {
        check(node, DIV);
        return new Div(node.getChildren().stream().map(this::parseArithExpr).toArray(AArithExpr[]::new));
    }

    private ABoolExpr parseBoolExpr(XMLNode node) {
        switch (node.getName()) {
            case AND:
                return parseAnd(node);
            case OR:
                return parseOr(node);
            case EQUALS:
                return parseEquals(node);
            case NEQ:
                return parseNEQ(node);
            case IMPLIES:
                return parseImplies(node);
            case FORALL:
                return parseForAll(node);
            case EXISTS:
                return parseExists(node);
            case PREDICATE:
                return parsePredicate(node);
            default:
                check(node, AND, OR, EQUALS, NEQ, IMPLIES, FORALL, EXISTS, PREDICATE);
                return null;
        }
    }

    private ABoolExpr parseAnd(XMLNode node) {
        check(node, AND);
        return new And(node.getChildren().stream().map(this::parseBoolExpr).toArray(ABoolExpr[]::new));
    }

    private ABoolExpr parseOr(XMLNode node) {
        check(node, OR);
        return new Or(node.getChildren().stream().map(this::parseBoolExpr).toArray(ABoolExpr[]::new));
    }

    private ABoolExpr parseEquals(XMLNode node) {
        check(node, EQUALS);
        return new Equals(node.getChildren().stream().map(this::parseArithExpr).toArray(AArithExpr[]::new));
    }

    private ABoolExpr parseNEQ(XMLNode node) {
        check(node, NEQ);
        return new NEQ(parseArithExpr(node.getChildren().get(0)), parseArithExpr(node.getChildren().get(1)));
    }

    private ABoolExpr parseImplies(XMLNode node) {
        check(node, IMPLIES);
        return new Implies(parseBoolExpr(node.getChildren().get(0)), parseBoolExpr(node.getChildren().get(1)));
    }

    private ABoolExpr parseExists(XMLNode node) {
        check(node, EXISTS);
        return new Exists(parseBoolExpr(node.getChildren().get(1)), parseVarsDefs(node.getChildren().get(0)).stream().map(tuple -> new Tuple2<>(new Var(tuple.getFirst()), tuple.getSecond())).toArray((IntFunction<Tuple2<Var, ASetExpr>[]>) Tuple2[]::new));
    }

    private ABoolExpr parseForAll(XMLNode node) {
        check(node, FORALL);
        return new ForAll(parseBoolExpr(node.getChildren().get(1)), parseVarsDefs(node.getChildren().get(0)).stream().map(tuple -> new Tuple2<>(new Var(tuple.getFirst()), tuple.getSecond())).toArray((IntFunction<Tuple2<Var, ASetExpr>[]>) Tuple2[]::new));
    }

    private Predicate parsePredicate(XMLNode node) {
        check(node, PREDICATE);
        return new Predicate(node.getAttributes().get(NAME), parseBoolExpr(node.getChildren().get(0)));
    }

    private ASetExpr parseSetExpr(XMLNode node) {
        switch (node.getName()) {
            case RANGE:
                return parseRange(node);
            case NAMED_SET:
                return parseNamedSet(node);
            case SET:
                return parseSet(node);
            case ENUM:
                return parseEnum(node);
            default:
                check(node, RANGE, NAMED_SET, SET);
                return null;
        }
    }

    private ASetExpr parseRange(XMLNode node) {
        check(node, RANGE);
        return new Range(parseArithExpr(node.getChildren().get(0)), parseArithExpr(node.getChildren().get(1)));
    }

    private ASetExpr parseNamedSet(XMLNode node) {
        check(node, NAMED_SET);
        return new NamedSet(node.getAttributes().get(NAME));
    }

    private ASetExpr parseSet(XMLNode node) {
        check(node, SET);
        return new Set(node.getChildren().stream().map(this::parseArithExpr).toArray(AArithExpr[]::new));
    }

    private ASetExpr parseEnum(XMLNode node) {
        check(node, ENUM);
        return new Enum(node.getChildren().stream().map(this::parseEnumValue).toArray(EnumValue[]::new));
    }

    private EnumValue parseEnumValue(XMLNode node) {
        check(node, ENUM_VALUE);
        return new EnumValue(node.getAttributes().get(NAME));
    }

    private ASubstitution parseSubstitution(XMLNode node) {
        switch (node.getName()) {
            case SKIP:
                return parseSkip(node);
            case ASSIGNMENTS:
                return parseAssignments(node);
            case ASSIGNMENT:
                return parseAssignment(node);
            case SELECT:
                return parseSelect(node);
            case CHOICE:
                return parseChoice(node);
            case ANY:
                return parseAny(node);
            default:
                check(node, SKIP, ASSIGNMENTS, ASSIGNMENT, SELECT, CHOICE, ANY);
                return null;
        }
    }

    private ASubstitution parseSkip(XMLNode node) {
        check(node, SKIP);
        return new Skip();
    }

    private ASubstitution parseAssignments(XMLNode node) {
        check(node, ASSIGNMENTS);
        return new Assignments(node.getChildren().stream().map(this::parseAssignment).toArray(Assignment[]::new));
    }

    private ASubstitution parseAssignment(XMLNode node) {
        check(node, ASSIGNMENT);
        return new Assignment(parseAssignable(node.getChildren().get(0)), parseArithExpr(node.getChildren().get(1)));
    }

    private ASubstitution parseSelect(XMLNode node) {
        check(node, SELECT);
        return new Select(parseBoolExpr(node.getChildren().get(0)), parseSubstitution(node.getChildren().get(1)));
    }

    private ASubstitution parseChoice(XMLNode node) {
        check(node, CHOICE);
        return new Choice(node.getChildren().stream().map(this::parseSubstitution).toArray(ASubstitution[]::new));
    }

    private ASubstitution parseAny(XMLNode node) {
        check(node, ANY);
        return new Any(parseBoolExpr(node.getChildren().get(1)), parseSubstitution(node.getChildren().get(2)), parseVarsDefs(node.getChildren().get(0)).stream().map(tuple -> new Tuple2<>(new Var(tuple.getFirst()), tuple.getSecond())).toArray((IntFunction<Tuple2<Var, ASetExpr>[]>) Tuple2[]::new));
    }

}

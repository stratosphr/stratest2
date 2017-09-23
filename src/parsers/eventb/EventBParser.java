package parsers.eventb;

import langs.eventb.Event;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.*;
import langs.eventb.exprs.bool.GEQ;
import langs.eventb.exprs.bool.GT;
import langs.eventb.exprs.bool.LEQ;
import langs.eventb.exprs.bool.LT;
import langs.eventb.exprs.bool.NEQ;
import langs.eventb.exprs.sets.*;
import langs.eventb.exprs.sets.Enum;
import langs.eventb.substitutions.*;
import parsers.xml.XMLDocument;
import parsers.xml.XMLNode;
import parsers.xml.XMLParser;
import utilities.Tuple;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
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

    private final String name;
    private LinkedHashMap<String, ASetExpr> setsDefs;
    private LinkedHashMap<String, AArithExpr> constsDefs;
    private final LinkedHashMap<String, ASetExpr> varsDefs;
    private final LinkedHashMap<String, Tuple<ASetExpr, ASetExpr>> funsDefs;
    private Invariant invariant;
    private ASubstitution initialisation;
    private LinkedHashSet<Event> events;

    private EventBParser() {
        this.name = "";
        this.setsDefs = new LinkedHashMap<>();
        this.constsDefs = new LinkedHashMap<>();
        this.varsDefs = new LinkedHashMap<>();
        this.funsDefs = new LinkedHashMap<>();
        this.invariant = new Invariant(new True());
        this.initialisation = new Skip();
        this.events = new LinkedHashSet<>();
    }

    public static Machine parse(File file) {
        EventBParser parser = new EventBParser();
        XMLDocument parse = XMLParser.parse(file, new File("resources/eventb/eventb.xsd"));
        return parser.parseModel(parse.getRoot());
    }

    private void check(XMLNode node, String... expected) throws Error {
        if (!Arrays.asList(expected).contains(node.getName())) {
            throw new Error("Node \"" + node.getName() + "\" was expected to be one of the following: " + Arrays.stream(expected).collect(Collectors.joining(", ")));
        }
    }

    private Machine parseModel(XMLNode node) {
        check(node, MODEL);
        List<XMLNode> setsDefsNodes = node.getChildren(SETS_DEFS);
        List<XMLNode> constsDefsNodes = node.getChildren(CONSTS_DEFS);
        List<XMLNode> varsDefsNodes = node.getChildren(VARS_DEFS);
        List<XMLNode> funsDefsNodes = node.getChildren(FUNS_DEFS);
        List<XMLNode> invariantNodes = node.getChildren(INVARIANT);
        List<XMLNode> initialisationNodes = node.getChildren(INITIALISATION);
        List<XMLNode> eventsNodes = node.getChildren(EVENTS);
        if (!setsDefsNodes.isEmpty()) {
            setsDefs = parseSetsDefs(setsDefsNodes.get(0));
        }
        if (!constsDefsNodes.isEmpty()) {
            constsDefs = parseConstsDefs(constsDefsNodes.get(0));
        }
        if (!varsDefsNodes.isEmpty()) {
            parseVarsDefs(varsDefsNodes.get(0)).forEach(varDef -> varsDefs.put(varDef.getFirst(), varDef.getSecond()));
        }
        if (!funsDefsNodes.isEmpty()) {
            parseFunsDefs(funsDefsNodes.get(0));
        }
        if (!invariantNodes.isEmpty()) {
            invariant = parseInvariant(invariantNodes.get(0));
        }
        if (!initialisationNodes.isEmpty()) {
            initialisation = parseInitialisation(initialisationNodes.get(0));
        }
        if (!eventsNodes.isEmpty()) {
            events = parseEvents(eventsNodes.get(0));
        }
        return Machine.getSingleton(name, setsDefs, constsDefs, varsDefs, funsDefs, invariant, initialisation, events);
    }

    private LinkedHashMap<String, ASetExpr> parseSetsDefs(XMLNode node) {
        check(node, SETS_DEFS);
        LinkedHashMap<String, ASetExpr> setsDefs = new LinkedHashMap<>();
        node.getChildren().forEach(child -> setsDefs.put(child.getAttributes().get(NAME), parseSetExpr(child.getChildren().get(0))));
        return setsDefs;
    }

    private LinkedHashMap<String, AArithExpr> parseConstsDefs(XMLNode node) {
        check(node, CONSTS_DEFS);
        LinkedHashMap<String, AArithExpr> constsDefs = new LinkedHashMap<>();
        node.getChildren().forEach(node1 -> constsDefs.put(node1.getAttributes().get(NAME), parseArithExpr(node1.getChildren().get(0))));
        return constsDefs;
    }

    private void parseConstDef(XMLNode node) {
        check(node, CONST_DEF);
        constsDefs.put(node.getAttributes().get(NAME), new Int(Integer.parseInt(node.getAttributes().get(VALUE))));
    }

    private LinkedHashSet<Tuple<String, ASetExpr>> parseVarsDefs(XMLNode node) {
        check(node, VARS_DEFS);
        return new LinkedHashSet<>(node.getChildren().stream().map(this::parseVarDef).collect(Collectors.toList()));
    }

    private Tuple<String, ASetExpr> parseVarDef(XMLNode node) {
        check(node, VAR_DEF);
        return new Tuple<>(node.getAttributes().get(NAME), parseSetExpr(node.getChildren().get(0)));
    }

    private void parseFunsDefs(XMLNode node) {
        check(node, FUNS_DEFS);
        node.getChildren().forEach(this::parseFunDef);
    }

    private void parseFunDef(XMLNode node) {
        check(node, FUN_DEF);
        funsDefs.put(node.getAttributes().get(NAME), new Tuple<>(parseSetExpr(node.getChildren().get(0)), parseSetExpr(node.getChildren().get(1))));
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
        return new LinkedHashSet<>(node.getChildren().stream().map(this::parseEvent).collect(Collectors.toList()));
    }

    private Event parseEvent(XMLNode node) {
        check(node, EVENT);
        return new Event(node.getAttributes().get(NAME), parseSubstitution(node.getChildren().get(0)));
    }

    private ASetExpr parseSetExpr(XMLNode node) {
        switch (node.getName()) {
            case SET:
                return parseSet(node);
            case ENUM:
                return parseEnum(node);
            case RANGE:
                return parseRange(node);
            case NAMED_SET:
                return parseNamedSet(node);
            default:
                check(node, SET, NAMED_SET, RANGE);
                return null;
        }
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
        return new EnumValue(node.getAttributes().get(NAME), EnumValue.getUniqueID());
    }

    private ASetExpr parseNamedSet(XMLNode node) {
        check(node, NAMED_SET);
        return new NamedSet(node.getAttributes().get(NAME));
    }

    private Range parseRange(XMLNode node) {
        check(node, RANGE);
        return new Range(parseArithExpr(node.getChildren().get(0)), parseArithExpr(node.getChildren().get(1)));
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

    private Int parseInt(XMLNode node) {
        check(node, INT);
        return new Int(Integer.parseInt(node.getAttributes().get(VALUE)));
    }

    private Const parseConst(XMLNode node) {
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

    private Plus parsePlus(XMLNode node) {
        check(node, PLUS);
        return new Plus(node.getChildren().stream().map(this::parseArithExpr).toArray(AArithExpr[]::new));
    }

    private Minus parseMinus(XMLNode node) {
        check(node, MINUS);
        return new Minus(node.getChildren().stream().map(this::parseArithExpr).toArray(AArithExpr[]::new));
    }

    private Times parseTimes(XMLNode node) {
        check(node, TIMES);
        return new Times(node.getChildren().stream().map(this::parseArithExpr).toArray(AArithExpr[]::new));
    }

    private Div parseDiv(XMLNode node) {
        check(node, DIV);
        return new Div(node.getChildren().stream().map(this::parseArithExpr).toArray(AArithExpr[]::new));
    }

    private ABoolExpr parseBoolExpr(XMLNode node) {
        switch (node.getName()) {
            case TRUE:
                return parseTrue(node);
            case FALSE:
                return parseFalse(node);
            case NOT:
                return parseNot(node);
            case AND:
                return parseAnd(node);
            case OR:
                return parseOr(node);
            case EQUALS:
                return parseEquals(node);
            case NEQ:
                return parseNEQ(node);
            case LT:
                return parseLT(node);
            case LEQ:
                return parseLEQ(node);
            case GT:
                return parseGT(node);
            case GEQ:
                return parseGEQ(node);
            case IMPLIES:
                return parseImplies(node);
            case EQUIV:
                return parseEquiv(node);
            case EXISTS:
                return parseExists(node);
            case FORALL:
                return parseForAll(node);
            default:
                check(node, TRUE, FALSE, NOT, AND, OR, EQUALS, LT, LEQ, GT, GEQ, IMPLIES, EQUIV, EXISTS, FORALL);
                return null;
        }
    }

    private True parseTrue(XMLNode node) {
        check(node, TRUE);
        return new True();
    }

    private False parseFalse(XMLNode node) {
        check(node, FALSE);
        return new False();
    }

    private Not parseNot(XMLNode node) {
        check(node, NOT);
        return new Not(parseBoolExpr(node.getChildren().get(0)));
    }

    private And parseAnd(XMLNode node) {
        check(node, AND);
        return new And(node.getChildren().stream().map(this::parseBoolExpr).toArray(ABoolExpr[]::new));
    }

    private Or parseOr(XMLNode node) {
        check(node, OR);
        return new Or(node.getChildren().stream().map(this::parseBoolExpr).toArray(ABoolExpr[]::new));
    }

    private Equals parseEquals(XMLNode node) {
        check(node, EQUALS);
        return new Equals(node.getChildren().stream().map(this::parseArithExpr).toArray(AArithExpr[]::new));
    }

    private ABoolExpr parseNEQ(XMLNode node) {
        check(node, NEQ);
        return new NEQ(node.getChildren().stream().map(this::parseArithExpr).toArray(AArithExpr[]::new));
    }

    private LT parseLT(XMLNode node) {
        check(node, LT);
        return new LT(parseArithExpr(node.getChildren().get(0)), parseArithExpr(node.getChildren().get(1)));
    }

    private LEQ parseLEQ(XMLNode node) {
        check(node, LEQ);
        return new LEQ(parseArithExpr(node.getChildren().get(0)), parseArithExpr(node.getChildren().get(1)));
    }

    private GT parseGT(XMLNode node) {
        check(node, GT);
        return new GT(parseArithExpr(node.getChildren().get(0)), parseArithExpr(node.getChildren().get(1)));
    }

    private GEQ parseGEQ(XMLNode node) {
        check(node, GEQ);
        return new GEQ(parseArithExpr(node.getChildren().get(0)), parseArithExpr(node.getChildren().get(1)));
    }

    private Implies parseImplies(XMLNode node) {
        check(node, IMPLIES);
        return new Implies(parseBoolExpr(node.getChildren().get(0)), parseBoolExpr(node.getChildren().get(1)));
    }

    private Equiv parseEquiv(XMLNode node) {
        check(node, EQUIV);
        return new Equiv(parseBoolExpr(node.getChildren().get(0)), parseBoolExpr(node.getChildren().get(1)));
    }

    private Exists parseExists(XMLNode node) {
        check(node, EXISTS);
        return new Exists(parseBoolExpr(node.getChildren().get(1)), parseVarsDefs(node.getChildren(VARS_DEFS).get(0)).stream().map(tuple -> new Tuple<>(new Var(tuple.getFirst()), tuple.getSecond())).toArray((IntFunction<Tuple<Var, ASetExpr>[]>) Tuple[]::new));
    }

    private ForAll parseForAll(XMLNode node) {
        check(node, FORALL);
        return new ForAll(parseBoolExpr(node.getChildren().get(1)), parseVarsDefs(node.getChildren(VARS_DEFS).get(0)).stream().map(tuple -> new Tuple<>(new Var(tuple.getFirst()), tuple.getSecond())).toArray((IntFunction<Tuple<Var, ASetExpr>[]>) Tuple[]::new));
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
            case IF_THEN_ELSE:
                return parseIfThenElse(node);
            case ANY:
                return parseAny(node);
            default:
                check(node, SKIP, ASSIGNMENTS, ASSIGNMENT, SELECT, IF_THEN_ELSE, ANY);
                return null;
        }
    }

    private Skip parseSkip(XMLNode node) {
        check(node, SKIP);
        return new Skip();
    }

    private Assignments parseAssignments(XMLNode node) {
        check(node, ASSIGNMENTS);
        return new Assignments(node.getChildren().stream().map(this::parseAssignment).toArray(Assignment[]::new));
    }

    private Assignment parseAssignment(XMLNode node) {
        check(node, ASSIGNMENT);
        return new Assignment(parseAssignable(node.getChildren().get(0)), parseArithExpr(node.getChildren().get(1)));
    }

    private Select parseSelect(XMLNode node) {
        check(node, SELECT);
        return new Select(parseBoolExpr(node.getChildren().get(0)), parseSubstitution(node.getChildren().get(1)));
    }

    private IfThenElse parseIfThenElse(XMLNode node) {
        check(node, IF_THEN_ELSE);
        return new IfThenElse(parseBoolExpr(node.getChildren().get(0)), parseSubstitution(node.getChildren().get(1)), parseSubstitution(node.getChildren().get(2)));
    }

    private Any parseAny(XMLNode node) {
        check(node, ANY);
        return new Any(parseBoolExpr(node.getChildren().get(1)), parseSubstitution(node.getChildren().get(2)), parseVarsDefs(node.getChildren(VARS_DEFS).get(0)).stream().map(tuple -> new Tuple<>(new Var(tuple.getFirst()), tuple.getSecond())).toArray((IntFunction<Tuple<Var, ASetExpr>[]>) Tuple[]::new));
    }

}

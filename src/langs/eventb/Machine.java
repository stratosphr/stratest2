package langs.eventb;

import formatters.eventb.IEventBVisitable;
import formatters.eventb.IEventBVisitor;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.*;
import langs.eventb.exprs.sets.ASetExpr;
import langs.eventb.substitutions.ASubstitution;
import langs.eventb.substitutions.Skip;
import utilities.sets.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 21/09/17.
 * Time : 09:49
 */
public final class Machine implements IEventBVisitable {

    private static String name = "";
    private static LinkedHashMap<String, AArithExpr> constsDefs = new LinkedHashMap<>();
    private static LinkedHashMap<String, Const> consts = new LinkedHashMap<>();
    private static LinkedHashMap<String, ASetExpr> setsDefs = new LinkedHashMap<>();
    private static LinkedHashMap<String, ASetExpr> sets = new LinkedHashMap<>();
    private static LinkedHashMap<String, ASetExpr> varsDefs = new LinkedHashMap<>();
    private static LinkedHashMap<String, Var> vars = new LinkedHashMap<>();
    private static LinkedHashMap<String, Tuple2<ASetExpr, ASetExpr>> funsDefs = new LinkedHashMap<>();
    private static Invariant invariant = new Invariant(new True());
    private static ASubstitution initialisation = new Skip();
    private static LinkedHashMap<String, Event> events = new LinkedHashMap<>();
    private static LinkedHashSet<AAssignable> assignables = new LinkedHashSet<>();

    private Machine() {
    }

    public static void setName(String name) {
        Machine.name = name;
    }

    public static String getName() {
        return name;
    }

    public static void addConstDef(String constName, AArithExpr value) {
        constsDefs.put(constName, value);
        consts.put(constName, new Const(constName));
    }

    public static LinkedHashMap<String, AArithExpr> getConstsDefs() {
        return constsDefs;
    }

    public static LinkedHashMap<String, Const> getConsts() {
        return consts;
    }

    public static void addSetDef(String setName, ASetExpr set) {
        setsDefs.put(setName, set);
        sets.put(setName, set);
    }

    public static LinkedHashMap<String, ASetExpr> getSetsDefs() {
        return setsDefs;
    }

    public static Map<String, ASetExpr> getSets() {
        return sets;
    }

    public static void addVarDef(String varName, ASetExpr set) {
        varsDefs.put(varName, set);
        vars.put(varName, new Var(varName));
        assignables.add(vars.get(varName));
    }

    public static LinkedHashMap<String, ASetExpr> getVarsDefs() {
        return varsDefs;
    }

    public static LinkedHashMap<String, Var> getVars() {
        return vars;
    }

    public static void addFunDef(String funName, Tuple2<ASetExpr, ASetExpr> domains) {
        funsDefs.put(funName, domains);
        assignables.addAll(new Fun(funName, null).getVars().stream().map(var -> new Fun(var.getName().split(Fun.getParameterDelimiter())[0], new Int(Integer.parseInt(var.getName().split(Fun.getParameterDelimiter())[1])))).collect(Collectors.toList()));
    }

    public static LinkedHashMap<String, Tuple2<ASetExpr, ASetExpr>> getFunsDefs() {
        return funsDefs;
    }

    public static void setInvariant(Invariant invariant) {
        List<ABoolExpr> constraints = new ArrayList<>();
        if (!constsDefs.isEmpty()) {
            And constsConstraints = new And(constsDefs.keySet().stream().map(constName -> new Equals(consts.get(constName), constsDefs.get(constName))).toArray(ABoolExpr[]::new));
            constraints.add(constsConstraints);
        }
        if (!varsDefs.isEmpty()) {
            And varsConstraints = new And(varsDefs.keySet().stream().map(varName -> new InDomain(vars.get(varName), varsDefs.get(varName))).toArray(ABoolExpr[]::new));
            constraints.add(varsConstraints);
        }
        if (!funsDefs.isEmpty()) {
            And funsConstraints = new And(funsDefs.keySet().stream().map(funName -> funsDefs.get(funName).getFirst().getSet().stream().map(value -> new InDomain(new Var(funName + Fun.getParameterDelimiter() + value), funsDefs.get(funName).getSecond())).collect(Collectors.toList())).flatMap(Collection::stream).toArray(ABoolExpr[]::new));
            constraints.add(funsConstraints);
        }
        constraints.add(invariant);
        Machine.invariant = new Invariant(new And(constraints.toArray(new ABoolExpr[constraints.size()])));
    }

    public static Invariant getInvariant() {
        return invariant;
    }

    public static void setInitialisation(ASubstitution initialisation) {
        Machine.initialisation = initialisation;
    }

    public static ASubstitution getInitialisation() {
        return initialisation;
    }

    public static void addEvent(Event event) {
        events.put(event.getName(), event);
    }

    public static LinkedHashMap<String, Event> getEvents() {
        return events;
    }

    public static LinkedHashSet<AAssignable> getAssignables() {
        return assignables;
    }

    @Override
    public String accept(IEventBVisitor visitor) {
        return visitor.visit(this);
    }

    public static void reset() {
        name = "";
        constsDefs = new LinkedHashMap<>();
        consts = new LinkedHashMap<>();
        setsDefs = new LinkedHashMap<>();
        sets = new LinkedHashMap<>();
        varsDefs = new LinkedHashMap<>();
        vars = new LinkedHashMap<>();
        funsDefs = new LinkedHashMap<>();
        invariant = new Invariant(new True());
        initialisation = new Skip();
        events = new LinkedHashMap<>();
        assignables = new LinkedHashSet<>();
    }

}

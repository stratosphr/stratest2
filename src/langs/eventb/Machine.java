package langs.eventb;

import formatters.eventb.IEventBVisitor;
import langs.eventb.exprs.arith.*;
import langs.eventb.exprs.bool.*;
import langs.eventb.exprs.sets.ASetExpr;
import langs.eventb.substitutions.ASubstitution;
import utilities.Tuple;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 21/09/17.
 * Time : 09:49
 */
public final class Machine extends AEventBObject {

    private static String name = "";
    private static LinkedHashMap<String, AArithExpr> constsDefs = new LinkedHashMap<>();
    private static LinkedHashMap<String, Const> consts = new LinkedHashMap<>();
    private static LinkedHashMap<String, ASetExpr> setsDefs = new LinkedHashMap<>();
    private static LinkedHashMap<String, ASetExpr> sets = new LinkedHashMap<>();
    private static LinkedHashMap<String, Tuple<ASetExpr, ASetExpr>> funsDefs = new LinkedHashMap<>();
    private static Invariant invariant;
    private static ASubstitution initialisation;
    private static LinkedHashSet<Event> events = new LinkedHashSet<>();
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

    public static void addFunDef(String funName, Tuple<ASetExpr, ASetExpr> domains) {
        funsDefs.put(funName, domains);
        assignables.addAll(new Fun(funName, null).getVars());
    }

    public static LinkedHashMap<String, Tuple<ASetExpr, ASetExpr>> getFunsDefs() {
        return funsDefs;
    }

    public static void setInvariant(Invariant invariant) {
        List<ABoolExpr> constraints = new ArrayList<>();
        if (!constsDefs.isEmpty()) {
            And constsConstraints = new And(constsDefs.keySet().stream().map(constName -> new Equals(consts.get(constName), constsDefs.get(constName))).toArray(ABoolExpr[]::new));
            constraints.add(constsConstraints);
        }
            /*if (!varsDefs.isEmpty()) {
                And varsConstraints = new And(varsDefs.keySet().stream().map(varName -> new InDomain(vars.get(varName), varsDefs.get(varName))).toArray(ABoolExpr[]::new));
                constraints.add(varsConstraints);
            }*/
        if (!funsDefs.isEmpty()) {
            And funsConstraints = new And(funsDefs.keySet().stream().map(funName -> funsDefs.get(funName).getFirst().getSet().stream().map(value -> new InDomain(new Var(funName + "!" + value), funsDefs.get(funName).getSecond())).collect(Collectors.toList())).flatMap(Collection::stream).toArray(ABoolExpr[]::new));
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
        events.add(event);
    }

    public static LinkedHashSet<Event> getEvents() {
        return events;
    }

    public static Machine getSingleton() {
        return new Machine();
    }

    public static Set<AAssignable> getAssignables() {
        return assignables;
    }

    @Override
    public String accept(IEventBVisitor visitor) {
        return visitor.visit(this);
    }

}

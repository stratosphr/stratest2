package langs.eventb;

import formatters.eventb.IEventBVisitor;
import langs.eventb.exprs.arith.AArithExpr;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Var;
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

    private final String name;
    private final LinkedHashMap<String, ASetExpr> setsDefs;
    private final LinkedHashMap<String, AArithExpr> constsDefs;
    private final LinkedHashSet<Event> events;
    private final ASubstitution initialisation;
    private final LinkedHashMap<String, ASetExpr> varsDefs;
    private final LinkedHashMap<String, Tuple<ASetExpr, ASetExpr>> funsDefs;
    private Invariant invariant;
    private final LinkedHashMap<String, Const> consts;
    private final LinkedHashMap<String, Var> vars;
    private static Machine singleton;
    private final Invariant tmpInvariant;
    private LinkedHashSet<AAssignable> assignables;

    private Machine(String name, LinkedHashMap<String, ASetExpr> setsDefs, LinkedHashMap<String, AArithExpr> constsDefs, LinkedHashMap<String, ASetExpr> varsDefs, LinkedHashMap<String, Tuple<ASetExpr, ASetExpr>> funsDefs, Invariant invariant, ASubstitution initialisation, LinkedHashSet<Event> events) {
        this.name = name;
        this.setsDefs = setsDefs;
        this.constsDefs = constsDefs;
        this.consts = new LinkedHashMap<>();
        this.varsDefs = varsDefs;
        this.vars = new LinkedHashMap<>();
        this.funsDefs = funsDefs;
        constsDefs.forEach((key, value) -> consts.put(key, new Const(key)));
        varsDefs.forEach((key, value) -> vars.put(key, new Var(key)));
        this.tmpInvariant = invariant;
        this.invariant = null;
        this.initialisation = initialisation;
        this.events = events;
    }

    public static Machine getSingleton() {
        if (singleton == null) {
            throw new Error("No machine was instanciated yet. This is required in order to have a working context of execution.");
        } else {
            return singleton;
        }
    }

    public static Machine getSingleton(String name, LinkedHashMap<String, ASetExpr> setsDefs, LinkedHashMap<String, AArithExpr> constsDefs, LinkedHashMap<String, ASetExpr> varsDefs, LinkedHashMap<String, Tuple<ASetExpr, ASetExpr>> funsDefs, Invariant invariant, ASubstitution initialisation, LinkedHashSet<Event> events) {
        if (singleton == null) {
            singleton = new Machine(name, setsDefs, constsDefs, varsDefs, funsDefs, invariant, initialisation, events);
        }
        return singleton;
    }

    @Override
    public String accept(IEventBVisitor visitor) {
        return visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public LinkedHashMap<String, ASetExpr> getSetsDefs() {
        return setsDefs;
    }

    public LinkedHashMap<String, AArithExpr> getConstsDefs() {
        return constsDefs;
    }

    public LinkedHashMap<String, ASetExpr> getVarsDefs() {
        return varsDefs;
    }

    public LinkedHashMap<String, Tuple<ASetExpr, ASetExpr>> getFunsDefs() {
        return funsDefs;
    }

    public LinkedHashMap<String, Const> getConsts() {
        return consts;
    }

    public LinkedHashMap<String, Var> getVars() {
        return vars;
    }

    public Invariant getInvariant() {
        if (invariant == null) {
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
                And funsConstraints = new And(funsDefs.keySet().stream().map(funName -> funsDefs.get(funName).getFirst().getSet().stream().map(value -> new InDomain(new Var(funName + "!" + value), funsDefs.get(funName).getSecond())).collect(Collectors.toList())).flatMap(Collection::stream).toArray(ABoolExpr[]::new));
                constraints.add(funsConstraints);
            }
            constraints.add(tmpInvariant);
            invariant = new Invariant(new And(constraints.toArray(new ABoolExpr[constraints.size()])));
        }
        return invariant;
    }

    public ASubstitution getInitialisation() {
        return initialisation;
    }

    public LinkedHashSet<Event> getEvents() {
        return events;
    }

    public LinkedHashSet<AAssignable> getAssignables() {
        return assignables;
    }

}

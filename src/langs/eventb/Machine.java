package langs.eventb;

import formatters.eventb.IEventBVisitor;
import langs.eventb.exprs.arith.Const;
import langs.eventb.exprs.arith.Int;
import langs.eventb.exprs.arith.Var;
import langs.eventb.exprs.bool.Invariant;
import langs.eventb.exprs.sets.ASetExpr;
import langs.eventb.substitutions.ASubstitution;
import utilities.Tuple;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 21/09/17.
 * Time : 09:49
 */
public final class Machine extends AEventBObject {

    private final String name;
    private final LinkedHashMap<String, Int> constsDefs;
    private final LinkedHashSet<Event> events;
    private final ASubstitution initialisation;
    private final LinkedHashMap<String, ASetExpr> varsDefs;
    private final LinkedHashMap<String, Tuple<ASetExpr, ASetExpr>> funsDefs;
    private final Invariant invariant;
    private final LinkedHashMap<String, Const> consts;
    private final LinkedHashMap<String, Var> vars;
    private static Machine singleton;

    private Machine(String name, LinkedHashMap<String, Int> constsDefs, LinkedHashMap<String, ASetExpr> varsDefs, LinkedHashMap<String, Tuple<ASetExpr, ASetExpr>> funsDefs, Invariant invariant, ASubstitution initialisation, LinkedHashSet<Event> events) {
        this.name = name;
        this.constsDefs = constsDefs;
        this.consts = new LinkedHashMap<>();
        constsDefs.forEach((key, value) -> consts.put(key, new Const(key)));
        this.varsDefs = varsDefs;
        this.vars = new LinkedHashMap<>();
        varsDefs.forEach((key, value) -> vars.put(key, new Var(key)));
        this.funsDefs = funsDefs;
        this.invariant = invariant;
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

    public static Machine getSingleton(String name, LinkedHashMap<String, Int> constsDefs, LinkedHashMap<String, ASetExpr> varsDefs, LinkedHashMap<String, Tuple<ASetExpr, ASetExpr>> funsDefs, Invariant invariant, ASubstitution initialisation, LinkedHashSet<Event> events) {
        if (singleton == null) {
            singleton = new Machine(name, constsDefs, varsDefs, funsDefs, invariant, initialisation, events);
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

    public LinkedHashMap<String, Int> getConstsDefs() {
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
        return invariant;
    }

    public ASubstitution getInitialisation() {
        return initialisation;
    }

    public LinkedHashSet<Event> getEvents() {
        return events;
    }

}

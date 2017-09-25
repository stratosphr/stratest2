import langs.eventb.Machine;
import langs.eventb.exprs.arith.AAssignable;
import langs.eventb.exprs.bool.And;
import parsers.eventb.MachineParser;
import solvers.z3.Z3;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        MachineParser.parse(new File("resources/eventb/L14/L14.ebm"));
        System.out.println(Z3.checkSAT(new And(
                Machine.getInvariant(),
                Machine.getInvariant().prime(),
                Machine.getInvariant().prime().prime(),
                Machine.getInvariant().prime().prime().prime(),
                Machine.getInvariant().prime().prime().prime().prime(),
                Machine.getInitialisation().getPrd(Machine.getAssignables()),
                Machine.getEvents().get("Ouverture_Portes").getSubstitution().getPrd(Machine.getAssignables()).prime(),
                Machine.getEvents().get("Fermeture_Portes").getSubstitution().getPrd(Machine.getAssignables()).prime().prime(),
                Machine.getEvents().get("Depart_Station").getSubstitution().getPrd(Machine.getAssignables()).prime().prime().prime()
        )));
        System.out.println(Z3.getModel(Machine.getAssignables().stream().map(assignable -> (AAssignable) assignable.prime()).collect(Collectors.toCollection(LinkedHashSet::new))));
        System.out.println(Z3.getModel(Machine.getAssignables().stream().map(assignable -> (AAssignable) assignable.prime().prime()).collect(Collectors.toCollection(LinkedHashSet::new))));
        System.out.println(Z3.getModel(Machine.getAssignables().stream().map(assignable -> (AAssignable) assignable.prime().prime().prime()).collect(Collectors.toCollection(LinkedHashSet::new))));
        System.out.println(Z3.getModel(Machine.getAssignables().stream().map(assignable -> (AAssignable) assignable.prime().prime().prime().prime()).collect(Collectors.toCollection(LinkedHashSet::new))));
    }

}

import com.microsoft.z3.Status;
import langs.eventb.Machine;
import langs.eventb.exprs.bool.And;
import parsers.eventb.MachineParser;
import solvers.z3.Z3;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        MachineParser.parse(new File("resources/eventb/L14/L14.ebm"));
        Status status = Z3.checkSAT(new And(
                Machine.getInvariant(),
                Machine.getInvariant().prime()
        ));
        if (status == Status.SATISFIABLE) {
            System.out.println(Z3.getModel(Machine.getAssignables()));
        }
    }

}

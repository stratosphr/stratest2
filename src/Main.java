import com.microsoft.z3.Status;
import langs.eventb.Machine;
import langs.eventb.exprs.arith.EnumValue;
import langs.eventb.exprs.arith.Fun;
import langs.eventb.exprs.arith.Int;
import langs.eventb.exprs.bool.And;
import langs.eventb.exprs.bool.Equals;
import parsers.eventb.MachineParser;
import solvers.z3.Z3;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        MachineParser.parse(new File("resources/eventb/EL/EL.ebm"));
        Status status = Z3.checkSAT(new And(
                Machine.getInvariant(),
                Machine.getInvariant().prime(),
                new Equals(new Fun("bat", new Int(1)), new EnumValue("off")),
                new Equals(new Fun("bat", new Int(2)), new EnumValue("off")),
                new Equals(new Fun("bat", new Int(3)), new EnumValue("off"))
        ));
        System.out.println(Z3.getModel(Machine.getAssignables()));
    }

}

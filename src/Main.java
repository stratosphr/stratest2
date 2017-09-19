import com.microsoft.z3.Status;
import langs.exprs.arith.Int;
import langs.exprs.arith.Var;
import langs.exprs.bool.*;
import parsers.xml.XMLDocument;
import parsers.xml.XMLNode;
import parsers.xml.XMLParser;
import solvers.z3.Z3;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        XMLDocument parse = XMLParser.parse(new File("resources/eventb/sample.ebm"), new File("resources/eventb/eventb.xsd"));
        List<XMLNode> children = parse.getRoot().getChildren("funs-defs").get(0).getChildren("fun-def");
        Exists exists = new Exists(
                new Or(
                        new Equals(
                                new Int(3),
                                new Var("var"),
                                new Var("val")
                        ),
                        new Equals(
                                new Int(2),
                                new Var("var"),
                                new Var("val")
                        )
                ),
                new Var("var")
        );
        ForAll forAll = new ForAll(
                new Implies(
                        new Equiv(
                                new Equals(new Var("var"), new Int(2)),
                                new Equals(new Var("result"), new Int(3))
                        ),
                        new Equiv(
                                new Equals(new Var("var"), new Int(3)),
                                new Equals(new Var("result"), new Int(4))
                        )
                ),
                new Var("var")
        );
        Status status = Z3.checkSAT(forAll);
        System.out.println(Z3.getModel(new LinkedHashSet<>(Collections.singletonList(new Var("result")))));
        System.out.println(status);
    }

}

import com.microsoft.z3.Status;
import langs.exprs.bool.False;
import solvers.z3.Z3;

public class Main {

    public static void main(String[] args) {
        Status status = Z3.checkSAT(new False());
        System.out.println(status);
    }

}

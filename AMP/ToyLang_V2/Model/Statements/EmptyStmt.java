package Model.Statements;

import Model.Exceptions.MyException;
import Model.PrgState;

public class EmptyStmt implements StmtInterface {
    @Override
    public PrgState execute(PrgState p) throws MyException {
        return p;
    }

    @Override
    public String toString() {
        return "";
    }
}

package Model.Statements;

import Model.PrgState;
import Model.Exceptions.MyException;

public interface StmtInterface {
    PrgState execute(PrgState p) throws MyException; //throws exception;
    String toString();
}

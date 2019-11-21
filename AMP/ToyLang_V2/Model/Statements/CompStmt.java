package Model.Statements;

import Model.PrgState;

import java.util.Stack;

public class CompStmt implements StmtInterface{
    private StmtInterface first;
    private StmtInterface second;

    public CompStmt(StmtInterface stmt1, StmtInterface stmt2){
        this.first = stmt1;
        this.second = stmt2;
    }

    public StmtInterface getFirst() {
        return first;
    }

    public StmtInterface getSecond() {
        return second;
    }

    @Override
    public PrgState execute(PrgState p) {
        Stack<StmtInterface> stk = p.getExeStack();
        stk.push(second);
        stk.push(first);
        return p;
    }

    @Override
    public String toString() {
        return "[" + first.toString() + ";" + second.toString() + "]";
    }
}

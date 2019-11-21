package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.ExpInterface;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.ValueInterface;

import java.util.Dictionary;
import java.util.Stack;

public class IfStmt implements StmtInterface {
    private ExpInterface exp;
    private StmtInterface stmt1;
    private StmtInterface stmt2;

    public IfStmt(ExpInterface exp, StmtInterface stmt1, StmtInterface stmt2) {
        this.exp = exp;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }

    public ExpInterface getExp() {
        return exp;
    }

    public void setExp(ExpInterface exp) {
        this.exp = exp;
    }

    public StmtInterface getStmt1() {
        return stmt1;
    }

    public void setStmt1(StmtInterface stmt1) {
        this.stmt1 = stmt1;
    }

    public StmtInterface getStmt2() {
        return stmt2;
    }

    public void setStmt2(StmtInterface stmt2) {
        this.stmt2 = stmt2;
    }

    @Override
    public PrgState execute(PrgState p) throws MyException {
        Stack<StmtInterface> executionStack = p.getExeStack();
        Dictionary<String, ValueInterface> symbolTable = p.getSymTable();
        ValueInterface value = exp.evaluate(symbolTable);

        if(value.getType().equals(new BoolType())){
            BoolValue condition = (BoolValue) value;
            if(condition.getVal()){
                executionStack.push(stmt1);
                p.setExeStack(executionStack);
            }
            else{
                executionStack.push(stmt2);
                p.setExeStack(executionStack);
            }
            return p;
        }
        else{
            throw new MyException("Wrong conditional type!");
        }
    }

    @Override
    public String toString() {
        return "if(" + exp + ") " + stmt1 + "; else " + stmt2 + ";";
    }
}

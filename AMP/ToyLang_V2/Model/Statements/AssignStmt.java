package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.ExpInterface;
import Model.PrgState;
import Model.Types.TypeInterface;
import Model.Values.ValueInterface;

import java.util.Dictionary;
import java.util.Stack;

public class AssignStmt implements StmtInterface {
    private String id;
    private ExpInterface exp;

    public AssignStmt(String id, ExpInterface exp) {
        this.id = id;
        this.exp = exp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ExpInterface getExp() {
        return exp;
    }

    public void setExp(ExpInterface exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState p) {
        Stack<StmtInterface> stack = p.getExeStack();
        Dictionary<String, ValueInterface> symbolTable = p.getSymTable();
        ValueInterface value = exp.evaluate(symbolTable);

        if(symbolTable.get(id) != null){
            TypeInterface typeId = (symbolTable.get(id)).getType();
            if(value.getType().equals(typeId)){
                symbolTable.put(id, value);
            }
            else{
                throw new MyException("Type not matching for variable: " + id);
            }
        }
        else throw new MyException("Referencing before declaration!");

        return p;
    }

    @Override
    public String toString() {
        return id + " = " + exp.toString();
    }
}

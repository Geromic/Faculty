package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.ExpInterface;
import Model.PrgState;
import Model.Values.ValueInterface;

import java.util.Dictionary;
import java.util.List;

public class PrintStmt implements StmtInterface {
    private ExpInterface exp;

    public PrintStmt(ExpInterface exp) {
        this.exp = exp;
    }

    public ExpInterface getExp() {
        return exp;
    }

    public void setExp(ExpInterface exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState p) throws MyException {
        Dictionary<String, ValueInterface> symbolTable = p.getSymTable();
        List<ValueInterface> output = p.getOut();

        ValueInterface value = exp.evaluate(symbolTable);
        output.add(value);
        p.setOut(output);

        return p;
    }

    @Override
    public String toString() {
        return "print(" + exp + ")";
    }
}

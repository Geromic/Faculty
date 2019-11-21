package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.ExpInterface;
import Model.PrgState;
import Model.Types.StringType;
import Model.Values.ValueInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Stack;

public class closeRFileStmt implements StmtInterface {
    private ExpInterface exp;

    public closeRFileStmt(ExpInterface exp) {
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
        Stack<StmtInterface> stack = p.getExeStack();
        Dictionary<String, ValueInterface> symbolTable = p.getSymTable();
        Dictionary<String, BufferedReader> fileTable = p.getFileTable();
        ValueInterface value = exp.evaluate(symbolTable);

        if(value.getType().equals(new StringType())) {
            BufferedReader buff = fileTable.get(value.toString());
            if (buff == null) throw new MyException("File not opened");
            fileTable.remove(value.toString());
            p.setFileTable(fileTable);

            try {
                buff.close();
            } catch (IOException e) {
                throw new MyException("Error closing file");
            }

            return p;
        }
        else throw new MyException("Expression is not of type string");
    }

    @Override
    public String toString() {
        return "close(" + exp + ")";
    }
}

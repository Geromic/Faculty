package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.ExpInterface;
import Model.PrgState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.ValueInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Stack;

public class openRFileStmt implements StmtInterface {
    private ExpInterface exp;

    public openRFileStmt(ExpInterface exp) {
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
        Dictionary<String, BufferedReader> fileTable = p.getFileTable();
        ValueInterface value = exp.evaluate(symbolTable);

        if(value.getType().equals(new StringType())) {
            StringValue sValue = (StringValue)value;
            Enumeration<String> keys = fileTable.keys();
            while(keys.hasMoreElements())  {
                String key = keys.nextElement();
                if (key.equals(value.toString())) {
                    throw new MyException("File already opened");
                }
            }
            try {
                BufferedReader buff = Files.newBufferedReader(Paths.get("C:/Users/Calutzu/IdeaProjects/A3_MAP/A2_MAP/A2_MAP/src/" + sValue.getVal()));
                p.pushFile(value.toString(), buff);
            } catch (IOException e) {
                throw new MyException("Error opening file");
            }
            return p;
        }
        else throw new MyException("Expression is not of type string");
    }

    @Override
    public String toString() {
        return "open(" + exp + ")";
    }
}

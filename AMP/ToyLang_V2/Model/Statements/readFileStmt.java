package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.ExpInterface;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.ValueInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Stack;

public class readFileStmt implements StmtInterface {
    private ExpInterface exp;
    private String id;

    public readFileStmt(ExpInterface exp, String id) {
        this.exp = exp;
        this.id = id;
    }

    public ExpInterface getExp() {
        return exp;
    }

    public void setExp(ExpInterface exp) {
        this.exp = exp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public PrgState execute(PrgState p) throws MyException {
        Stack<StmtInterface> stack = p.getExeStack();
        Dictionary<String, ValueInterface> symbolTable = p.getSymTable();
        Dictionary<String, BufferedReader> fileTable = p.getFileTable();
        ValueInterface value = exp.evaluate(symbolTable);

        ValueInterface idVal = symbolTable.get(id);
        if(idVal.getType().equals(new IntType())){
            if(value.getType().equals(new StringType())){
                IntValue idInt = (IntValue)idVal;
                StringValue valueString = (StringValue)value;

                BufferedReader buff = fileTable.get(value.toString());
                if (buff == null) throw new MyException("File not opened");

                try {
                    String line = buff.readLine();
                    IntValue newInt;
                    if(line == null)
                        newInt = (IntValue) new IntType().defaultValue();
                    else{
                        newInt = new IntValue(Integer.parseInt(line));
                    }

                    symbolTable.put(id, newInt);
                    p.setSymTable(symbolTable);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new MyException("Error reading from file");
                }

                return p;
            }
            else{
              throw new MyException("Expression is not of type string") ;
            }
        }
        else{
            throw new MyException("Mismatch type for variable: " + id);
        }
    }

    @Override
    public String toString() {
        return id +" = readLine(" + exp + ")";
    }
}

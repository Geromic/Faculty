package Model.Statements;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.TypeInterface;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.ValueInterface;

import java.util.Dictionary;

public class DeclStmt implements StmtInterface {
    private TypeInterface type;
    private String id;

    public DeclStmt(TypeInterface type, String id) {
        this.type = type;
        this.id = id;
    }

    public TypeInterface getType() {
        return type;
    }

    public void setType(TypeInterface type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public PrgState execute(PrgState p) throws MyException {
        Dictionary<String, ValueInterface> symbolTable = p.getSymTable();

        if(symbolTable.get(id)==null){
            symbolTable.put(id, type.defaultValue());
            return p;
        }
        else {
            throw new MyException("Variable already defined!");
        }
    }

    @Override
    public String toString() {
        return type + " " + id;
    }
}

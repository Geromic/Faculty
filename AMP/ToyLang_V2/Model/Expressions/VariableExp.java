package Model.Expressions;

import Model.Exceptions.MyException;
import Model.Values.ValueInterface;

import java.util.Dictionary;

public class VariableExp implements ExpInterface{
    private String id;

    public VariableExp(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public ValueInterface evaluate(Dictionary<String, ValueInterface> table) {
        if(table.get(id) != null){
            return table.get(id);
        }
        else throw new MyException(id + " is not defined!");
    }

    @Override
    public String toString() {
        return id;
    }
}

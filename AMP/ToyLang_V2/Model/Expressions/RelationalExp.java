package Model.Expressions;

import Model.Exceptions.MyException;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.ValueInterface;

import java.util.Dictionary;

public class RelationalExp implements ExpInterface{
    private ExpInterface first, second;
    private int op; //{(1:<) ,(2:>) ,(3:<=) ,(4:>=) ,(5:==) ,(6:!=)}

    public RelationalExp(ExpInterface first, ExpInterface second, int op) {
        this.first = first;
        this.second = second;
        if(op < 1 || op > 6)
            throw new MyException("Unknown comparison operation");
        this.op = op;
    }

    @Override
    public ValueInterface evaluate(Dictionary<String, ValueInterface> table) {
        BoolValue result = new BoolValue(true);

        ValueInterface val1 = first.evaluate(table);
        if(!val1.getType().equals(new IntType())){
            throw new MyException("First operand is not an integer");
        }

        ValueInterface val2 = second.evaluate(table);
        if(!val2.getType().equals(new IntType())){
            throw new MyException("Second operand is not an integer");
        }

        IntValue int1 = (IntValue)val1;
        IntValue int2 = (IntValue)val2;

        switch(op){
            case 1: return new BoolValue(int1.getVal() < int2.getVal());
            case 2: return new BoolValue(int1.getVal() > int2.getVal());
            case 3: return new BoolValue(int1.getVal() <= int2.getVal());
            case 4: return new BoolValue(int1.getVal() >= int2.getVal());
            case 5: return new BoolValue(int1.getVal() == int2.getVal());
            case 6: return new BoolValue(int1.getVal() != int2.getVal());
        }

        return new BoolValue(false);
    }

    @Override
    public String toString() {
        switch(op){
            case(1): return first + " < " + second;
            case(2): return first + " > " + second;
            case(3): return first + " <= " + second;
            case(4): return first + " >= " + second;
            case(5): return first + " == " + second;
            case(6): return first + " != " + second;
        }
        return "";
    }
}

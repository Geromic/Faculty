package Model.Expressions;

import Model.Exceptions.MyException;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.ValueInterface;

import java.util.Dictionary;
import java.util.List;

public class LogicExp implements ExpInterface{
    private ExpInterface first, second;
    private int op;//{(0,&&),(1,||),(2,==)}

    public LogicExp(ExpInterface first, ExpInterface second, int op) {
        this.first = first;
        this.second = second;
        this.op = op;
    }

    @Override
    public ValueInterface evaluate(Dictionary<String, ValueInterface> table){
        ValueInterface firstValue = first.evaluate(table);
        ValueInterface secondValue = second.evaluate(table);

        if(firstValue.getType().equals(new BoolType())){
            if(secondValue.getType().equals(new BoolType())){

                BoolValue boolValue1 = (BoolValue)firstValue;
                BoolValue boolValue2 = (BoolValue)secondValue;

                switch(op){
                    case 0: return boolValue1.and(boolValue2);
                    case 1: return boolValue1.or(boolValue2);
                    case 2: return boolValue1.eq(boolValue2);
                }
            }
            else throw new MyException("Second var is not bool");
        }
        else throw new MyException("First var is not bool");

        return new BoolValue(false);
    }

    @Override
    public String toString(){
        switch(op){
            case 0: return first + "&&" + second;
            case 1: return first + "||" + second;
            case 2: return first + "==" + second;
        }
        throw new MyException("Operand unknown!");
    }

    public ExpInterface getFirst() {
        return first;
    }

    public void setFirst(ExpInterface first) {
        this.first = first;
    }

    public ExpInterface getSecond() {
        return second;
    }

    public void setSecond(ExpInterface second) {
        this.second = second;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }
}

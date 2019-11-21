package Model.Expressions;

import Model.Types.IntType;
import Model.Values.ValueInterface;
import Model.Values.IntValue;
import Model.Exceptions.MyException;

import java.util.Dictionary;

public class ArithmExp implements ExpInterface{
    private ExpInterface first;
    private ExpInterface second;
    private int op; //{(1,+),(2,-),(3,*),(4,/)}

    public ArithmExp(ExpInterface first, ExpInterface second, int op) {
        this.first = first;
        this.second = second;
        this.op = op;
    }

    @Override
    public ValueInterface evaluate(Dictionary<String, ValueInterface> table) throws MyException
    {
        ValueInterface v1, v2;
        v1 = first.evaluate(table);
        if(v1.getType().equals(new IntType())){
            v2 = second.evaluate((table));
            if(v2.getType().equals(new IntType())){
                IntValue int1 = (IntValue)v1;
                IntValue int2 = (IntValue)v2;
                int a,b;
                a= int1.getVal();
                b = int2.getVal();
                if(op==1) return new IntValue(a+b);
                if(op==2) return new IntValue(a-b);
                if(op==3) return new IntValue(a*b);
                if(op==4)
                    if(b==0) throw new MyException("Div by 0");
                    else return new IntValue(a/b);
            }
            else{
                throw new MyException("Second op is not an integer!");
            }
        }
        else{
            throw new MyException("First op is not an integer!");
        }
        return new IntValue(0);
    }

    @Override
    public String toString() {
        switch(op){
            case 1 : return first + "+" + second;
            case 2 : return first + "-" + second;
            case 3 : return first + "*" + second;
            case 4 : return first + "/" + second;
        }
        return null;
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

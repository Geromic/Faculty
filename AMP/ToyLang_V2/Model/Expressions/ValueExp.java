package Model.Expressions;

import Model.Values.ValueInterface;

import java.util.Dictionary;

public class ValueExp implements ExpInterface{
    private ValueInterface val;

    public ValueExp(ValueInterface val) {
        this.val = val;
    }

    public ValueInterface getVal() {
        return val;
    }

    public void setVal(ValueInterface val) {
        this.val = val;
    }

    @Override
    public ValueInterface evaluate(Dictionary<String, ValueInterface> table) {
        return val;
    }

    @Override
    public String toString() {
        return val.toString();
    }
}

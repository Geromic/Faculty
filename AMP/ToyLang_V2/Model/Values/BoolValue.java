package Model.Values;

import Model.Types.BoolType;
import Model.Types.TypeInterface;

public class BoolValue implements ValueInterface{
    private boolean val;

    public BoolValue(boolean val) {
        this.val = val;
    }

    @Override
    public TypeInterface getType() {
        return new BoolType();
    }

    public boolean getVal() {
        return val;
    }

    public void setVal(boolean val) {
        this.val = val;
    }

    @Override
    public String toString() {
        if (val) return "true";
        return "false";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoolValue boolValue = (BoolValue) o;
        return val == boolValue.val;
    }

    public ValueInterface and(BoolValue other){
        return new BoolValue(this.val && other.getVal());
    }

    public ValueInterface or(BoolValue other){
        return new BoolValue(this.val || other.getVal());
    }

    public ValueInterface eq(BoolValue other){
        return new BoolValue(this.val == other.getVal());
    }
}

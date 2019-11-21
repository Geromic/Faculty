package Model.Values;

import Model.Types.IntType;
import Model.Types.TypeInterface;

public class IntValue implements ValueInterface{
    private int val;

    public IntValue(int val) {
        this.val = val;
    }

    @Override
    public TypeInterface getType() {
        return new IntType();
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntValue)) return false;
        IntValue intValue = (IntValue) o;
        return val == intValue.val;
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }
}

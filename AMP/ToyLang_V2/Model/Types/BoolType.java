package Model.Types;

import Model.Values.BoolValue;
import Model.Values.ValueInterface;

public class BoolType implements TypeInterface{
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public ValueInterface defaultValue() {
        return new BoolValue(true);
    }
}

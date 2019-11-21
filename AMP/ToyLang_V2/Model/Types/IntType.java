package Model.Types;

import Model.Values.IntValue;
import Model.Values.ValueInterface;

public class IntType implements TypeInterface{
    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public ValueInterface defaultValue() {
        return new IntValue(0);
    }
}

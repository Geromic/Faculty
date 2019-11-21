package Model.Types;

import Model.Values.StringValue;
import Model.Values.ValueInterface;

public class StringType implements TypeInterface {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public ValueInterface defaultValue() {
        return new StringValue("");
    }
}

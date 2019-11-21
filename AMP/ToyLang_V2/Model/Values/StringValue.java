package Model.Values;

import Model.Types.StringType;
import Model.Types.TypeInterface;

public class StringValue implements ValueInterface {
    private String val;

    public StringValue(String val) {
        this.val = val;
    }

    @Override
    public TypeInterface getType() {
        return new StringType();
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringValue)) return false;
        StringValue that = (StringValue) o;
        return val.equals(that.val);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return '"' + val + '"';
    }
}

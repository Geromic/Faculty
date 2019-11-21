package Model.Expressions;

import Model.Values.ValueInterface;

import java.util.Dictionary;

public interface ExpInterface {
    ValueInterface evaluate(Dictionary<String, ValueInterface> table);
    String toString();
}

package interpreter.model.exp;

import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record ValueExp(IValue value) implements IExp {
//creates an expression that stores a constant value.
    //new ValueExp(new IntValue(5)) = toy language expression for '5'

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) {
        return value;
    }
      @Override
    public String toString() {
        return value.toString();
    }
}

package interpreter.model.type;

import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

//record is a special kind of Java class that automatically provides useful methods
//such as equals(), hashCode(), and toString() (although here we override toString()).
public record IntType() implements IType {
    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public String toString(){
        return "int";
    }
}

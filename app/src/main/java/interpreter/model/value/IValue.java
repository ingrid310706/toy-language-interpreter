package interpreter.model.value;

import interpreter.model.type.IType;

public interface IValue {
    IType getType();
    //Every value knows which type it belongs to. An IntValue has type IntType
}

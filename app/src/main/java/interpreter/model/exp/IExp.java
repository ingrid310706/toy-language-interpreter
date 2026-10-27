package interpreter.model.exp;

import interpreter.model.value.IValue;
import interpreter.model.adt.IDictionary;
import interpreter.exceptions.MyException;


public interface IExp {
    IValue eval(IDictionary<String,IValue> symTable)
            //eval calculates the result of an expression.
        throws MyException;
}

package interpreter.model.exp;

import interpreter.model.value.IValue;
import interpreter.model.adt.IDictionary;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IHeap;

public interface IExp {
    IValue eval(IDictionary<String,IValue> symTable,IHeap heap)
            //eval calculates the result of an expression.
        throws MyException;
}

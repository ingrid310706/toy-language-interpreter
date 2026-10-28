
package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.BoolValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarExpTest {

    @Test
    void evaluatesExistingIntegerVariable() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        symTable.put("x", new IntValue(10));

        IExp exp = new VarExp("x");

        assertEquals(new IntValue(10), exp.eval(symTable));
    }

    @Test
    void evaluatesExistingBooleanVariable() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        symTable.put("a", new BoolValue(true));

        IExp exp = new VarExp("a");

        assertEquals(new BoolValue(true), exp.eval(symTable));
    }

    @Test
    void undefinedVariableThrowsException() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();

        IExp exp = new VarExp("x");

        ExpressionException ex = assertThrows(
                ExpressionException.class,
                () -> exp.eval(symTable)
        );

        assertEquals("variable x is not defined", ex.getMessage());
    }

    @Test
    void evaluationDoesNotModifySymbolTable() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        symTable.put("x", new IntValue(10));

        IExp exp = new VarExp("x");
        exp.eval(symTable);

        assertEquals(new IntValue(10), symTable.lookup("x"));
    }

    @Test
    void toStringReturnsVariableName() {
        IExp exp = new VarExp("x");

        assertEquals("x", exp.toString());
    }
}

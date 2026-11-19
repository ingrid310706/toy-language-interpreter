
package interpreter.model.exp;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.BoolValue;
import org.junit.jupiter.api.Test;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyHeap;

import static org.junit.jupiter.api.Assertions.*;

class ValueExpTest {

    @Test
    void evaluatesIntegerValue() throws Exception {
        IDictionary<String, IValue> symTable = new MyDictionary<>();

        IExp exp = new ValueExp(new IntValue(5));

        assertEquals(new IntValue(5), exp.eval(symTable, new MyHeap()));
    }

    @Test
    void evaluatesBooleanValue() throws Exception {
        IDictionary<String, IValue> symTable = new MyDictionary<>();

        IExp exp = new ValueExp(new BoolValue(true));

        assertEquals(new BoolValue(true), exp.eval(symTable, new MyHeap()));
    }

    @Test
    void evaluationDoesNotModifySymbolTable() throws Exception {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        symTable.put("x", new IntValue(10));

        IExp exp = new ValueExp(new IntValue(5));
        exp.eval(symTable, new MyHeap());

        assertEquals(new IntValue(10), symTable.lookup("x"));
    }

    @Test
    void toStringReturnsValueAsText() {
        IExp exp = new ValueExp(new IntValue(5));

        assertEquals("5", exp.toString());
    }
}


package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithExpTest {

    private final IDictionary<String, IValue> symTable =
            new MyDictionary<>();

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    // Professor's example: 2 + 3 * 5 = 17
    @Test
    void respectsOperatorPrecedenceEncodedInTheTree() throws MyException {
        IExp e = new ArithExp(
                ArithOp.ADD,
                num(2),
                new ArithExp(ArithOp.MUL, num(3), num(5))
        );

        assertEquals(new IntValue(17), e.eval(symTable));
    }

    // Professor's example: division by zero
    @Test
    void divisionByZeroThrows() {
        IExp e = new ArithExp(ArithOp.DIV, num(1), num(0));

        var ex = assertThrows(
                ExpressionException.class,
                () -> e.eval(symTable)
        );

        assertEquals("division by zero", ex.getMessage());
    }

    @Test
    void additionWorks() throws MyException {
        IExp e = new ArithExp(ArithOp.ADD, num(5), num(3));

        assertEquals(new IntValue(8), e.eval(symTable));
    }

    @Test
    void subtractionWorks() throws MyException {
        IExp e = new ArithExp(ArithOp.SUB, num(10), num(4));

        assertEquals(new IntValue(6), e.eval(symTable));
    }

    @Test
    void multiplicationWorks() throws MyException {
        IExp e = new ArithExp(ArithOp.MUL, num(3), num(4));

        assertEquals(new IntValue(12), e.eval(symTable));
    }

    @Test
    void divisionWorks() throws MyException {
        IExp e = new ArithExp(ArithOp.DIV, num(10), num(2));

        assertEquals(new IntValue(5), e.eval(symTable));
    }
}

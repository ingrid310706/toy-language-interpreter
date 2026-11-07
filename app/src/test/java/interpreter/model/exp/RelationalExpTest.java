
package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {

    private BoolValue evaluate(RelOp op, int a, int b)
            throws MyException {

        RelationalExp exp = new RelationalExp(
                op,
                new ValueExp(new IntValue(a)),
                new ValueExp(new IntValue(b))
        );

        return (BoolValue) exp.eval(new MyDictionary<>());
    }

    @Test
    void lessThanTrue() throws MyException {
        assertEquals(new BoolValue(true),
                evaluate(RelOp.LT, 5, 7));
    }

    @Test
    void lessThanFalse() throws MyException {
        assertEquals(new BoolValue(false),
                evaluate(RelOp.LT, 7, 5));
    }

    @Test
    void lessOrEqualTrue() throws MyException {
        assertEquals(new BoolValue(true),
                evaluate(RelOp.LE, 5, 5));
    }

    @Test
    void lessOrEqualFalse() throws MyException {
        assertEquals(new BoolValue(false),
                evaluate(RelOp.LE, 7, 5));
    }

    @Test
    void equalTrue() throws MyException {
        assertEquals(new BoolValue(true),
                evaluate(RelOp.EQ, 5, 5));
    }

    @Test
    void equalFalse() throws MyException {
        assertEquals(new BoolValue(false),
                evaluate(RelOp.EQ, 5, 7));
    }

    @Test
    void notEqualTrue() throws MyException {
        assertEquals(new BoolValue(true),
                evaluate(RelOp.NE, 5, 7));
    }

    @Test
    void notEqualFalse() throws MyException {
        assertEquals(new BoolValue(false),
                evaluate(RelOp.NE, 5, 5));
    }

    @Test
    void greaterThanTrue() throws MyException {
        assertEquals(new BoolValue(true),
                evaluate(RelOp.GT, 7, 5));
    }

    @Test
    void greaterThanFalse() throws MyException {
        assertEquals(new BoolValue(false),
                evaluate(RelOp.GT, 5, 7));
    }

    @Test
    void greaterOrEqualTrue() throws MyException {
        assertEquals(new BoolValue(true),
                evaluate(RelOp.GE, 5, 5));
    }

    @Test
    void greaterOrEqualFalse() throws MyException {
        assertEquals(new BoolValue(false),
                evaluate(RelOp.GE, 5, 7));
    }

    @Test
    void firstOperandNotIntegerThrows() {
        RelationalExp exp = new RelationalExp(
                RelOp.LT,
                new ValueExp(new BoolValue(true)),
                new ValueExp(new IntValue(5))
        );

        assertThrows(ExpressionException.class,
                () -> exp.eval(new MyDictionary<>()));
    }

    @Test
    void secondOperandNotIntegerThrows() {
        RelationalExp exp = new RelationalExp(
                RelOp.LT,
                new ValueExp(new IntValue(5)),
                new ValueExp(new BoolValue(true))
        );

        assertThrows(ExpressionException.class,
                () -> exp.eval(new MyDictionary<>()));
    }
}

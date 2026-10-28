
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

class LogicExpTest {

    private final IDictionary<String, IValue> symTable =
            new MyDictionary<>();

    private static IExp bool(boolean value) {
        return new ValueExp(new BoolValue(value));
    }

    @Test
    void andReturnsTrueWhenBothOperandsAreTrue() throws MyException {
        IExp exp = new LogicExp(LogicOp.AND, bool(true), bool(true));

        assertEquals(new BoolValue(true), exp.eval(symTable));
    }

    @Test
    void andReturnsFalseWhenOneOperandIsFalse() throws MyException {
        IExp exp = new LogicExp(LogicOp.AND, bool(true), bool(false));

        assertEquals(new BoolValue(false), exp.eval(symTable));
    }

    @Test
    void orReturnsTrueWhenOneOperandIsTrue() throws MyException {
        IExp exp = new LogicExp(LogicOp.OR, bool(false), bool(true));

        assertEquals(new BoolValue(true), exp.eval(symTable));
    }

    @Test
    void orReturnsFalseWhenBothOperandsAreFalse() throws MyException {
        IExp exp = new LogicExp(LogicOp.OR, bool(false), bool(false));

        assertEquals(new BoolValue(false), exp.eval(symTable));
    }

    @Test
    void firstOperandNotBooleanThrows() {
        IExp exp = new LogicExp(
                LogicOp.AND,
                new ValueExp(new IntValue(5)),
                bool(true)
        );

        ExpressionException exception = assertThrows(
                ExpressionException.class,
                () -> exp.eval(symTable)
        );

        assertEquals("first operand is not a boolean", exception.getMessage());
    }

    @Test
    void secondOperandNotBooleanThrows() {
        IExp exp = new LogicExp(
                LogicOp.OR,
                bool(true),
                new ValueExp(new IntValue(5))
        );

        ExpressionException exception = assertThrows(
                ExpressionException.class,
                () -> exp.eval(symTable)
        );

        assertEquals("second operand is not a boolean", exception.getMessage());
    }

    @Test
    void logicOperatorsDisplayCorrectSymbols() {
        assertEquals("and", LogicOp.AND.toString());
        assertEquals("or", LogicOp.OR.toString());
    }
}

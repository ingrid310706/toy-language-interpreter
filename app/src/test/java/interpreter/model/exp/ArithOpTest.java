

package interpreter.model.exp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArithOpTest {

    @Test
    void arithmeticOperatorsHaveCorrectSymbols() {
        assertEquals("+", ArithOp.ADD.toString());
        assertEquals("-", ArithOp.SUB.toString());
        assertEquals("*", ArithOp.MUL.toString());
        assertEquals("/", ArithOp.DIV.toString());
    }
}

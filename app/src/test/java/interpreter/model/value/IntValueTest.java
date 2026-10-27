
package interpreter.model.value;

import interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntValueTest {

    @Test
    void getTypeReturnsIntType() {
        IntValue value = new IntValue(5);

        assertEquals(new IntType(), value.getType());
    }

    @Test
    void storesCorrectInteger() {
        IntValue value = new IntValue(5);

        assertEquals(5, value.val());
    }

    @Test
    void toStringReturnsIntegerAsText() {
        IntValue value = new IntValue(5);

        assertEquals("5", value.toString());
    }
}

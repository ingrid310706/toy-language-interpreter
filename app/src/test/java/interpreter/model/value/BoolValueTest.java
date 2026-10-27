
package interpreter.model.value;

import interpreter.model.type.BoolType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoolValueTest {

    @Test
    void getTypeReturnsBoolType() {
        BoolValue value = new BoolValue(true);

        assertEquals(new BoolType(), value.getType());
    }

    @Test
    void storesCorrectBoolean() {
        BoolValue value = new BoolValue(true);

        assertEquals(true, value.val());
    }

    @Test
    void toStringReturnsBooleanAsText() {
        BoolValue value = new BoolValue(true);

        assertEquals("true", value.toString());
    }

    @Test
    void falseValueDisplaysCorrectly() {
        BoolValue value = new BoolValue(false);

        assertEquals("false", value.toString());
    }
}

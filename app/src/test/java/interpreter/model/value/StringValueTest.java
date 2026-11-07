
package interpreter.model.value;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {

    @Test
    void equalStringValuesAreEqual() {
        StringValue first = new StringValue("test.in");
        StringValue second = new StringValue("test.in");

        assertEquals(first, second);
    }

    @Test
    void equalStringValuesHaveSameHashCode() {
        StringValue first = new StringValue("test.in");
        StringValue second = new StringValue("test.in");

        assertEquals(first.hashCode(), second.hashCode());
    }
}

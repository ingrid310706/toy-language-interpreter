
package interpreter.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MyExceptionTest {

    @Test
    void storesErrorMessage() {
        MyException exception = new MyException("Test error");

        assertEquals("Test error", exception.getMessage());
    }
}

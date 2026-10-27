
package interpreter.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ADTExceptionTest {

    @Test
    void storesErrorMessage() {
        ADTException exception = new ADTException("Key not found");

        assertEquals("Key not found", exception.getMessage());
    }

    @Test
    void extendsMyException() {
        ADTException exception = new ADTException("Error");

        assertInstanceOf(MyException.class, exception);
    }
}

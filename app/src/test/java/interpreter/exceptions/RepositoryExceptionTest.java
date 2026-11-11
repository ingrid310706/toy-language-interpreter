package interpreter.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RepositoryExceptionTest {

    @Test
    void storesErrorMessage() {
        RepositoryException exception = new RepositoryException("cannot write");

        assertEquals("cannot write", exception.getMessage());
    }

    @Test
    void extendsMyException() {
        RepositoryException exception = new RepositoryException("Error");

        assertInstanceOf(MyException.class, exception);
    }
}
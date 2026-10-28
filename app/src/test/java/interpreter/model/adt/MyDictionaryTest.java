
package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {

    @Test
    void putAndLookupWork() throws ADTException {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        dict.put("x", 5);

        assertEquals(5, dict.lookup("x"));
    }

    @Test
    void isDefinedWorks() {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        dict.put("x", 5);

        assertTrue(dict.isDefined("x"));
        assertFalse(dict.isDefined("y"));
    }

    @Test
    void updateChangesExistingValue() throws ADTException {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        dict.put("x", 5);
        dict.update("x", 10);

        assertEquals(10, dict.lookup("x"));
    }

    @Test
    void lookupMissingKeyThrows() {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        assertThrows(ADTException.class, () -> dict.lookup("y"));
    }

    @Test
    void updateMissingKeyThrows() {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        assertThrows(ADTException.class,
                () -> dict.update("y", 10));
    }
}

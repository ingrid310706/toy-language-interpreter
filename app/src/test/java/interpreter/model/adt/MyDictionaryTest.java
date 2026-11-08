
package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void removeExistingKeyWorks() throws ADTException {
        MyDictionary<String, Integer> dict = new MyDictionary<>();

        dict.put("x", 10);
        dict.remove("x");

        assertFalse(dict.isDefined("x"));
    }

    @Test
    void removeMissingKeyThrows() {
        MyDictionary<String, Integer> dict = new MyDictionary<>();

        assertThrows(ADTException.class, () -> dict.remove("x"));
    }

    @Test
    void toMapReturnsDictionaryContents() {
        MyDictionary<String, Integer> dict = new MyDictionary<>();

        dict.put("x", 10);
        dict.put("y", 20);

        Map<String, Integer> copy = dict.toMap();

        assertEquals(10, copy.get("x"));
        assertEquals(20, copy.get("y"));
    }

    @Test
    void toMapReturnsReadOnlyCopy() {
        MyDictionary<String, Integer> dict = new MyDictionary<>();

        dict.put("x", 10);

        Map<String, Integer> copy = dict.toMap();

        assertThrows(UnsupportedOperationException.class,
                () -> copy.put("y", 20));

        dict.put("z", 30);

        assertFalse(copy.containsKey("z"));
    }

}

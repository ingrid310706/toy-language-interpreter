
package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {

    @Test
    void addAndGetWork() throws ADTException {
        IList<Integer> list = new MyList<>();
        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void sizeReturnsCorrectNumberOfElements() {
        IList<Integer> list = new MyList<>();
        assertEquals(0, list.size());

        list.add(5);
        assertEquals(1, list.size());

        list.add(10);
        assertEquals(2, list.size());
    }

    @Test
    void negativeIndexThrowsException() {
        IList<Integer> list = new MyList<>();
        list.add(10);
        assertThrows(ADTException.class, () -> list.get(-1));
    }

    @Test
    void indexGreaterThanSizeThrowsException() {
        IList<Integer> list = new MyList<>();
        list.add(10);
        assertThrows(ADTException.class, () -> list.get(2));
    }

    @Test
    void indexEqualToSizeThrowsException() {
        IList<Integer> list = new MyList<>();

        list.add(10);

        assertThrows(ADTException.class, () -> list.get(1));
    }
}

package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {

    private IHeap heap;

    @BeforeEach
    void setUp() {
        heap = new MyHeap();
    }

    @Test
    void allocateReturnsTheAddressesOneTwoThree() {
        assertEquals(1, heap.allocate(new IntValue(10)));
        assertEquals(2, heap.allocate(new IntValue(20)));
        assertEquals(3, heap.allocate(new IntValue(30)));
    }

    @Test
    void getReturnsTheValueStoredAtTheAddress() throws ADTException {
        int address = heap.allocate(new IntValue(20));

        assertEquals(new IntValue(20), heap.get(address));
    }

    @Test
    void getOfAMissingAddressThrows() {
        assertThrows(ADTException.class, () -> heap.get(1));
    }

    @Test
    void addressZeroIsNeverDefined() {
        heap.allocate(new IntValue(1));

        assertFalse(heap.isDefined(0));
        assertThrows(ADTException.class, () -> heap.get(0));
    }

    @Test
    void updateChangesTheValueAtTheAddress() throws ADTException {
        int address = heap.allocate(new IntValue(20));

        heap.update(address, new IntValue(30));

        assertEquals(new IntValue(30), heap.get(address));
    }

    @Test
    void updateOfAMissingAddressThrows() {
        assertThrows(ADTException.class,
                () -> heap.update(5, new IntValue(1)));
    }

    @Test
    void isDefinedTellsWhetherTheAddressIsInTheHeap() {
        int address = heap.allocate(new IntValue(1));

        assertTrue(heap.isDefined(address));
        assertFalse(heap.isDefined(address + 1));
    }

    @Test
    void getContentIsAReadOnlyCopy() {
        heap.allocate(new IntValue(1));
        Map<Integer, IValue> content = heap.getContent();

        assertThrows(UnsupportedOperationException.class,
                () -> content.put(9, new IntValue(9)));
        assertFalse(heap.isDefined(9));
    }

    @Test
    void setContentReplacesTheWholeHeap() throws ADTException {
        heap.allocate(new IntValue(1));
        heap.allocate(new IntValue(2));

        heap.setContent(Map.of(2, new IntValue(2)));

        assertFalse(heap.isDefined(1));
        assertEquals(new IntValue(2), heap.get(2));
    }

    @Test
    void addressesAreNotReusedAfterTheContentIsReplaced() {
        heap.allocate(new IntValue(1));
        heap.allocate(new IntValue(2));
        heap.setContent(Map.of());

        assertEquals(3, heap.allocate(new IntValue(3)));
    }

    @Test
    void toStringShowsTheContent() {
        heap.allocate(new IntValue(20));

        assertEquals("{1=20}", heap.toString());
    }
}
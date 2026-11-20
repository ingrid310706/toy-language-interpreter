package interpreter.controller;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GarbageCollectorTest {

    private static final RefValue REF_INT_1 = new RefValue(1, new IntType());

    // Ex9 after new(v, 20); new(a, v); new(v, 30):
    //   v = (3, int), a = (2, Ref(int)), heap: 1 -> 20, 2 -> (1, int), 3 -> 30
    private final Map<Integer, IValue> heap = Map.of(
            1, new IntValue(20),
            2, REF_INT_1,
            3, new IntValue(30));
    private final List<IValue> symTable = List.of(
            new RefValue(3, new IntType()),
            new RefValue(2, new RefType(new IntType())));

    @Test
    void addressesInTakesOnlyReferencesAndIgnoresNull() {
        var values = List.<IValue>of(new IntValue(5), new RefValue(4, new IntType()),
                new RefValue(0, new IntType()));

        assertEquals(Set.of(4), GarbageCollector.addressesIn(values));
    }

    @Test
    void unsafeCollectorLosesTheLocationOnlyReachableThroughTheHeap() {
        Map<Integer, IValue> result = GarbageCollector.unsafeGarbageCollector(symTable, heap);

        assertEquals(Set.of(2, 3), result.keySet());
    }

    @Test
    void safeCollectorKeepsTheLocationsReachableThroughTheHeap() {
        Map<Integer, IValue> result = GarbageCollector.safeGarbageCollector(symTable, heap);

        assertEquals(Set.of(1, 2, 3), result.keySet());
        assertEquals(new IntValue(20), result.get(1));
    }

    @Test
    void safeCollectorRemovesWhatNothingPointsTo() {
        var symTableOnlyV = List.<IValue>of(new RefValue(3, new IntType()));

        Map<Integer, IValue> result = GarbageCollector.safeGarbageCollector(symTableOnlyV, heap);

        assertEquals(Set.of(3), result.keySet());
    }

    @Test
    void reachableAddressesFollowsAChainOfReferences() {
        Map<Integer, IValue> chain = Map.of(
                1, new RefValue(2, new IntType()),
                2, new RefValue(3, new IntType()),
                3, new IntValue(0),
                4, new IntValue(9));

        assertEquals(Set.of(1, 2, 3), GarbageCollector.reachableAddresses(Set.of(1), chain));
    }

    @Test
    void reachableAddressesTerminatesOnACycle() {
        Map<Integer, IValue> cycle = Map.of(
                1, new RefValue(2, new IntType()),
                2, new RefValue(1, new IntType()));

        assertEquals(Set.of(1, 2), GarbageCollector.reachableAddresses(Set.of(1), cycle));
    }

    @Test
    void nothingIsKeptWhenThereAreNoRoots() {
        assertTrue(GarbageCollector.safeGarbageCollector(List.of(), heap).isEmpty());
    }
}
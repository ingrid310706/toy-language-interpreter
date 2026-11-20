package interpreter.controller;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class GarbageCollector {

    private GarbageCollector() {
    }

    // the addresses that occur directly in the given values (only RefValues have one);
    // address 0 is null, it never points into the heap
    public static Set<Integer> addressesIn(Collection<IValue> values) {
        return values.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).address())
                .filter(a -> a != 0)
                .collect(Collectors.toSet());
    }

    // unsafe: keeps only the addresses that occur directly in the symbol table
    public static Map<Integer, IValue> unsafeGarbageCollector(
            Collection<IValue> symTableValues, Map<Integer, IValue> heap) {
        Set<Integer> roots = addressesIn(symTableValues);
        return heap.entrySet().stream()
                .filter(e -> roots.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // safe: also keeps the addresses reachable through the heap
    public static Map<Integer, IValue> safeGarbageCollector(
            Collection<IValue> symTableValues, Map<Integer, IValue> heap) {
        Set<Integer> reachable = reachableAddresses(addressesIn(symTableValues), heap);
        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // the roots plus every address found inside the heap values of the reachable addresses,
    // repeated until no new address appears
    public static Set<Integer> reachableAddresses(Set<Integer> roots,
                                                  Map<Integer, IValue> heap) {
        Set<Integer> reachable = new HashSet<>(roots);
        boolean changed = true;
        while (changed) {
            Set<Integer> found = addressesIn(
                    reachable.stream()
                            .filter(heap::containsKey)
                            .map(heap::get)
                            .collect(Collectors.toList()));
            changed = reachable.addAll(found);
        }
        return reachable;
    }
}
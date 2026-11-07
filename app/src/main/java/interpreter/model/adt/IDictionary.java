package interpreter.model.adt;
import interpreter.exceptions.ADTException;

import java.util.Map;


public interface IDictionary<K,V> {
    boolean isDefined(K key);
    V lookup(K key)
        throws ADTException;
    void put (K key , V value);
    void update(K key , V value)
            throws ADTException;
    // ex: a-> 5 ( key = a , value = 5)
    void remove(K key)
        throws ADTException;

    Map<K,V> toMap();

}

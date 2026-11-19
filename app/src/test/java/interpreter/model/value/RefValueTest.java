package interpreter.model.value;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefValueTest {

    @Test
    void theTypeIsARefToTheLocationType() {
        assertEquals(new RefType(new IntType()),
                new RefValue(1, new IntType()).getType());
    }

    @Test
    void aRefToARefHasTypeRefRefInt() {
        assertEquals(
                new RefType(new RefType(new IntType())),
                new RefValue(10, new RefType(new IntType())).getType()
        );
    }

    @Test
    void valuesWithTheSameAddressAndTypeAreEqual() {
        assertEquals(new RefValue(2, new IntType()),
                new RefValue(2, new IntType()));
        assertNotEquals(new RefValue(2, new IntType()),
                new RefValue(3, new IntType()));
    }

    @Test
    void toStringIsThePairAddressAndType() {
        assertEquals("(1, int)", new RefValue(1, new IntType()).toString());
        assertEquals("(10, Ref(int))",
                new RefValue(10, new RefType(new IntType())).toString());
    }
}
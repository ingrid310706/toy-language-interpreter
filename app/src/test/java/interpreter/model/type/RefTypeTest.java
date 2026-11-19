package interpreter.model.type;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefTypeTest {

    @Test
    void refVariableGetsTheDefaultValueZeroAndItsType() throws MyException {
        PrgState state = PrgState.of(new NopStmt());

        new VarDeclStmt("x", new RefType(new IntType())).execute(state);

        assertEquals(
                new RefValue(0, new IntType()),
                state.getSymTable().lookup("x")
        );
    }

    @Test
    void nestedRefTypesAreComparedRecursively() {
        assertEquals(
                new RefType(new RefType(new IntType())),
                new RefType(new RefType(new IntType()))
        );
    }

    @Test
    void refOfRefIntDiffersFromRefInt() {
        assertNotEquals(
                new RefType(new RefType(new IntType())),
                new RefType(new IntType())
        );
    }

    @Test
    void refTypesWithDifferentInnerTypesDiffer() {
        assertNotEquals(
                new RefType(new IntType()),
                new RefType(new BoolType())
        );
    }

    @Test
    void defaultValueOfARefRefIsNullWithTheInnerRefType() {
        RefType type = new RefType(new RefType(new IntType()));

        assertEquals(
                new RefValue(0, new RefType(new IntType())),
                type.defaultValue()
        );
    }

    @Test
    void toStringShowsTheNesting() {
        assertEquals("Ref(Ref(int))",
                new RefType(new RefType(new IntType())).toString());
    }
}
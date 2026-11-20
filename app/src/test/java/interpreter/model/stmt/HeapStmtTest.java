package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapStmtTest {

    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new RefType(new IntType())).execute(state);   // Ref int v
    }

    // ---------- new (S11)

    @Test
    void newAllocatesAtAddressOneAndUpdatesTheVariable() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);

        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    @Test
    void newTwiceUsesTwoDifferentAddressesAndLeavesTheOldOneInTheHeap()
            throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        new NewStmt("v", new ValueExp(new IntValue(30))).execute(state);

        assertEquals(new RefValue(2, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));
        assertEquals(new IntValue(30), state.getHeap().get(2));
    }

    @Test
    void newOfAReferenceToAReference() throws MyException {
        new VarDeclStmt("a", new RefType(new RefType(new IntType()))).execute(state);
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);

        new NewStmt("a", new VarExp("v")).execute(state);

        assertEquals(new RefValue(2, new RefType(new IntType())),
                state.getSymTable().lookup("a"));
        assertEquals(new RefValue(1, new IntType()), state.getHeap().get(2));
    }

    @Test
    void newWithAnUndeclaredVariableThrows() {
        assertThrows(StatementException.class,
                () -> new NewStmt("zzz", new ValueExp(new IntValue(1))).execute(state));
    }

    @Test
    void newWithAVariableThatIsNotAReferenceThrows() throws MyException {
        new VarDeclStmt("n", new IntType()).execute(state);

        assertThrows(StatementException.class,
                () -> new NewStmt("n", new ValueExp(new IntValue(1))).execute(state));
    }

    @Test
    void newWithAValueOfTheWrongTypeThrows() {
        var exp = new ValueExp(new BoolValue(true));

        assertThrows(StatementException.class,
                () -> new NewStmt("v", exp).execute(state));
        assertTrue(state.getHeap().getContent().isEmpty());
    }

    @Test
    void newToStringIsInfixForm() {
        assertEquals("new(v, 20)",
                new NewStmt("v", new ValueExp(new IntValue(20))).toString());
    }

    // ---------- wH (S12)

    @Test
    void wHChangesTheHeapLocation() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);

        new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state);

        assertEquals(new IntValue(30), state.getHeap().get(1));
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
    }

    @Test
    void wHWithAnUndeclaredVariableThrows() {
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("zzz", new ValueExp(new IntValue(1))).execute(state));
    }

    @Test
    void wHWithAVariableThatIsNotAReferenceThrows() throws MyException {
        new VarDeclStmt("n", new IntType()).execute(state);

        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("n", new ValueExp(new IntValue(1))).execute(state));
    }

    @Test
    void wHWithAnAddressThatIsNotInTheHeapThrows() {
        // v still holds (0, int): address 0 is null, never in the heap
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new IntValue(1))).execute(state));
    }

    @Test
    void wHWithAValueOfTheWrongTypeThrowsAndKeepsTheOldValue() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);

        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new BoolValue(true)))
                        .execute(state));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    @Test
    void wHToStringIsInfixForm() {
        assertEquals("wH(v, 30)",
                new WriteHeapStmt("v", new ValueExp(new IntValue(30))).toString());
    }
}
package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompStmtTest {

    private static IStmt dummyStmt(String name) {
        return new IStmt() {
            @Override
            public PrgState execute(PrgState state) {
                return state;
            }

            @Override
            public String toString() {
                return name;
            }
        };
    }

    @Test
    void pushesStatementsInCorrectOrder() throws MyException {
        IStmt first = dummyStmt("first");
        IStmt second = dummyStmt("second");

        IStmt compound = new CompStmt(first, second);

        PrgState state = PrgState.of(compound);

        // Simulate the controller popping the compound statement.
        state.getExeStack().pop();

        compound.execute(state);

        assertSame(first, state.getExeStack().pop());
        assertSame(second, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void executeReturnsSameProgramState() throws MyException {
        IStmt compound = new CompStmt(
                dummyStmt("first"),
                dummyStmt("second")
        );

        PrgState state = PrgState.of(compound);

        state.getExeStack().pop();

        assertSame(state, compound.execute(state));
    }

    @Test
    void toStringDisplaysBothStatements() {
        IStmt compound = new CompStmt(
                dummyStmt("first"),
                dummyStmt("second")
        );

        assertEquals("(first; second)", compound.toString());
    }
}
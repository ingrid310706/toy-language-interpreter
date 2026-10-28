
package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyStack;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
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

        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                compound
        );

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

        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                compound
        );

        state.getExeStack().pop();

        assertSame(state, compound.execute(state));
    }

    //when a compound statement executes, the second statement is pushed first,
    // then the first statement, so the first will execute next.

    @Test
    void toStringDisplaysBothStatements() {
        IStmt compound = new CompStmt(
                dummyStmt("first"),
                dummyStmt("second")
        );

        assertEquals("(first; second)", compound.toString());
    }
}


package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NopStmtTest {

    private PrgState createState() {
        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );
    }

    @Test
    void executeReturnsSameState() throws MyException {
        PrgState state = createState();
        IStmt stmt = new NopStmt();

        assertSame(state, stmt.execute(state));
    }

    @Test
    void executeDoesNotModifyState() throws MyException {
        PrgState state = createState();
        state.getSymTable().put("x", new IntValue(5));
        state.getOut().add(new IntValue(10));

        IStmt originalTop = state.getExeStack().pop();
        state.getExeStack().push(originalTop);

        IStmt stmt = new NopStmt();
        stmt.execute(state);

        assertSame(originalTop, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());

        assertEquals(new IntValue(5),
                state.getSymTable().lookup("x"));

        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(10), state.getOut().get(0));
    }

    @Test
    void toStringDisplaysNop() {
        IStmt stmt = new NopStmt();

        assertEquals("no operation", stmt.toString());
    }
}

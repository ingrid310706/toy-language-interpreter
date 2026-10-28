
package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStmtTest {

    private PrgState createState() throws MyException {
        IStmt dummy = state -> state;

        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                dummy
        );

        // Simulate the controller removing the current statement.
        state.getExeStack().pop();

        return state;
    }

    @Test
    void trueConditionPushesThenBranch() throws MyException {
        PrgState state = createState();

        IStmt thenStmt = s -> s;
        IStmt elseStmt = s -> s;

        IStmt stmt = new IfStmt(
                new ValueExp(new BoolValue(true)),
                thenStmt,
                elseStmt
        );

        stmt.execute(state);

        assertSame(thenStmt, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void falseConditionPushesElseBranch() throws MyException {
        PrgState state = createState();

        IStmt thenStmt = s -> s;
        IStmt elseStmt = s -> s;

        IStmt stmt = new IfStmt(
                new ValueExp(new BoolValue(false)),
                thenStmt,
                elseStmt
        );

        stmt.execute(state);

        assertSame(elseStmt, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void nonBooleanConditionThrows() throws MyException {
        PrgState state = createState();

        IStmt stmt = new IfStmt(
                new ValueExp(new IntValue(5)),
                s -> s,
                s -> s
        );

        StatementException ex = assertThrows(
                StatementException.class,
                () -> stmt.execute(state)
        );

        assertEquals("if condition must be boolean", ex.getMessage());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void variableConditionWorks() throws MyException {
        PrgState state = createState();

        state.getSymTable().put("a", new BoolValue(true));

        IStmt thenStmt = s -> s;
        IStmt elseStmt = s -> s;

        IStmt stmt = new IfStmt(
                new VarExp("a"),
                thenStmt,
                elseStmt
        );

        stmt.execute(state);

        assertSame(thenStmt, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }
}
